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

	private String triple = "x y z";
	private String stringA = "λy.(y y)";
	private String stringB = "((λx.(λy. (y  y))) v)";
	private String stringC = "(λx. ((x  x) x))";
	private String stringD = "((λx.λy.λz. z) (λx. ((x  x) x)))";
	private String stringE = "((λx. x (λy. y (λz.x z))) (x λx.v))";
	private String stringF = "((\\x. (v x)) v)";
	private String errorA = "λλx.(x y)";
	private String uselessBrackets = "((x) v)";
	private String mismatchedBrackets = "\\v. x )";
	private String id = "(λx.x)";
	private String app2 = id + id;
	private String app3 = id + id + id;
	private String app4 = id + id + id + id;

	LambdaTerm termA = new Abstraction("y", new Application(new BoundVariable(1), new BoundVariable(1)));
	LambdaTerm termB = new Application(new Abstraction("x", termA), new FreeVariable("v"));
	LambdaTerm termC = new Abstraction("x",
			new Application(new Application(new BoundVariable(1), new BoundVariable(1)), new BoundVariable(1)));
	LambdaTerm termD = new Application(
			new Abstraction("x", new Abstraction("y", new Abstraction("z", new BoundVariable(1)))), termC);
	LambdaTerm termF = new Application(
			new Abstraction("x", new Application(new FreeVariable("v"), new BoundVariable(1))), new FreeVariable("v"));
	LambdaTerm idTerm = new Abstraction("x", new BoundVariable(1));
	LambdaTerm app2Term = new Application(idTerm, idTerm);
	LambdaTerm app3Term = new Application(new Application(idTerm, idTerm), idTerm);
	LambdaTerm app4Term = new Application(new Application(new Application(idTerm, idTerm), idTerm), idTerm);

	@Before
	public void setUp() {
		testParser = new Parser(new ArrayList<Library>());
	}
	
	@Test
	public void testidentity() {
		try {
			LambdaTerm term = testParser.parse(id);
			assertEquals(term, idTerm);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test2App() {
		try {
			LambdaTerm term = testParser.parse(app2);
			assertEquals(term, app2Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void test3App() {
		try {
			LambdaTerm term = testParser.parse(app3);
			assertEquals(term, app3Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test4App() {
		try {
			LambdaTerm term = testParser.parse(app4);
			assertEquals(term, app4Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
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

	@Test
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

	@Test(expected = ParseException.class)
	public void failureTestCaseA() throws ParseException {
		testParser.parse(errorA);
	}

	@Test
	public void failureTestCaseB() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(errorA);
			fail();
		} catch (ParseException e) {
			assertEquals(e.getMessage(), "Unexpected token, expected VARIABLE");
		}
	}
	
	@Test(expected=ParseException.class)
	public void failureBrackets() throws ParseException {
		testParser.parse(uselessBrackets);
	}
	
	@Test(expected=ParseException.class)
	public void failureMismatch() throws ParseException {
		testParser.parse(mismatchedBrackets);
	}

	@Test
	public void firstLibraryTest() throws ParseException {
		String testString = "lib = λx.x" + "\n" + "λv.(lib lib)";
		LambdaTerm libTerm = new NamedTerm("lib", new Abstraction("x", new BoundVariable(1)));
		LambdaTerm expectedTerm = new Abstraction("v", new Application(libTerm, libTerm));
		LambdaTerm term = null;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(term, expectedTerm);
	}
	
	@Test
	public void multipleLibTermTest() {
		
	}
}
