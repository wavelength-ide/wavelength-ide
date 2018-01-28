package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A class containing tests to test the parser.
 *
 */
public class ParserTest {

	@Test
	public void testcaseA() {
		String testString = "((λx.(λy. (y  y))) v)";
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
}
