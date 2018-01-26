package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokeniserTest {

	private static String newLine = System.getProperty(line.seperator);
	private static String termA = "identity = (λx. x)" + newLine + "one =  (λs. (λz. s (s z))" + newLine + "((λz. z z) v)" ;
	private static String termB = "five =  (λs. (λz. s z)) ";
	private static String termC = "";
	private static String termD = "";

	
	@Test
	public void tokeniseLibraryTest() {
		Tokeniser tok = new Tokeniser();
		String testString = "identity = (λx. x)" + newLine;
		Token[] result = tok.tokeniseLibrary(testString);
		assertEquals(result.length, 3);
		assertEquals(result[0], "identity = (λx. x)");
		assertEquals(result[1], "one = (λs. (λz. s (s z))");
		assertEquals(result[2], "((λz. z z) v)");
	}
	public void tokLibrarySpacesTest() {
		
	}
}
