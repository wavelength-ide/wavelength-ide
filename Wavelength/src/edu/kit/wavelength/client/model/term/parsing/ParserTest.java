package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.CustomLibrary;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.BoundVariable;

/**
 * A class containing parser test.
 *
 */
public class ParserTest {

	@Test
	public void testcaseA() {
		String testString = "((λx.(λy. (y  y))) v)";
		Parser testParser = new Parser(new ArrayList<Library>());
		LambdaTerm expectedTerm;
		expectedTerm = new Application(new Abstraction("x", new Abstraction("y", new Application(new BoundVariable(2), new BoundVariable(2)))) ,new FreeVariable("v"));
		LambdaTerm term = null;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(term,expectedTerm);
	}

	@Test
	public void testcaseB() {
		String testString = "((λx.(λy.(λz. z))) (λx. ((x  x) x)))";
		Parser testParser = new Parser(new ArrayList<Library>());
		LambdaTerm term;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void droppedBracketsTestA() {
		String testString = "λx.λy.(v t)";
		Parser testParser = new Parser(new ArrayList<Library>());
		LambdaTerm term;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
	}

	@Test(expected = ParseException.class)
	public void droppedBracketsTestB() throws ParseException {
		String testString = "λx. x y";
		new Parser(new ArrayList<Library>()).parse(testString);
		fail();
	}
	
	@Test
	public void droppedBracketsTestC() throws ParseException {
		String testString = "lib = λx.x" + System.getProperty("line.separator") + " λv.(lib lib)";
		LambdaTerm term;
		try {
			Parser testParser = new Parser(new ArrayList<Library>());
			term = testParser.parse(testString);
		} catch (ParseException e) {
			fail();
		}
	}
}
