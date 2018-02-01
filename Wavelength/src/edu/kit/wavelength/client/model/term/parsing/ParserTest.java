package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.BoundVariable;

/**
 * A class containing parser tests.
 *
 */
public class ParserTest {

	Parser testParser;

	String stringA = "λy.(y y)";
	String stringB = "((λx.(λy. (y  y))) v)";
	String stringC = "(λx. ((x  x) x))";
	String stringD = "((λx.λy.λz. z) (λx. ((x  x) x)))";
	String stringE = "((λx. x (λy. y (λz.x z))) (x λx.v))";
	String stringF = "((\\x. (v x)) v)";
	String errorA = "λλx.(x y)";
	String errorB = "something";
	String errorC = "((x) v)";
	String errorD = "\\v. x )";

	LambdaTerm termA = new Abstraction("y", new Application(new BoundVariable(1), new BoundVariable(1)));
	LambdaTerm termB = new Application(new Abstraction("x", termA), new FreeVariable("v"));
	LambdaTerm termC = new Abstraction("x",
			new Application(new Application(new BoundVariable(1), new BoundVariable(1)), new BoundVariable(1)));
	LambdaTerm termD = new Application(
			new Abstraction("x", new Abstraction("y", new Abstraction("z", new BoundVariable(1)))), termC);
	LambdaTerm termF = new Application(
			new Abstraction("x", new Application(new FreeVariable("v"), new BoundVariable(1))), new FreeVariable("v"));

	@Before
	public void setUp() {
		testParser = new Parser(new ArrayList<Library>());
	}

	@Test
	public void testcaseA() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringA);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termA);
	}

	@Test
	public void testcaseB() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringB);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termB);
	}

	public void testCaseBwSpaces() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringB + "     ");
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termB);
	}

	@Test
	public void testcaseC() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringC);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termC);
	}

	@Test
	public void testcaseD() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringD);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termD);
	}

	@Test
	public void testCaseF() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringF);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termF);
	}

	@Test(expected=ParseException.class)
	public void failureTestCaseA() throws ParseException {
			LambdaTerm term = testParser.parse(errorA);
	}

	@Test
	public void failiureTestCaseB() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(errorA);
			fail();
		} catch (ParseException e) {
			assertEquals(e.getMessage(), "Unexpected token, expected VARIABLE");
		}
	}

	@Test
	public void libraryTest() throws ParseException {
		String testString = "lib = λx.x" + System.getProperty("line.separator") + " λv.(lib lib)";
		LambdaTerm libTerm = new NamedTerm("lib", new Abstraction("x", new BoundVariable(1)));
		LambdaTerm expectedTerm = new Abstraction("v", new Application(libTerm, libTerm));
		LambdaTerm term = null;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			fail();
		}
		assertEquals(term, expectedTerm);
	}
}
