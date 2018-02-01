package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TokeniserTest {

	private static String newLine = System.getProperty("line.seperator");
	private static String termA = "identity = (λx. x)" + newLine + "one = "
			+ " (λs. (λz. s (s z))" + newLine + "((λz. z z) v)";
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
	
	@Test
	public void tokeniseTestA() {
		String testString = "((λv.(x y)) (λz.(z z))";
		Token[] result = null;
		try {
			result = new Tokeniser().tokenise(testString);
		} catch (ParseException e) {
			fail();
		}
		assertEquals(result.length, 19);
		String[] expected = {"(", "(", "λ", "v", ".", "(", "x", "y", ")", ")", "(", "λ", "z", ".", "(", "z", "z", ")", ")"};
		for (int i = 0; i < expected.length; i++) {
			assertEquals(result[i].getContent(), expected[i]);
		}
	}
	
	@Test
	public void tokeniseSpacesTest() {
		String testString = "(  (λx    .    x)   v)";
		Token[] result = null;
		try {
			result = new Tokeniser().tokenise(testString);
		} catch (ParseException e) {
			fail();
		}
		String[] expected = {"(", "(", "λ", "x", ".", "x",")", "v", ")"};
		assertEquals(result.length, expected.length);
		for (int i = 0; i < expected.length; i++) {
			System.out.println(i);
			assertEquals(result[i].getContent(), expected[i]);
		}
	}
	
}
