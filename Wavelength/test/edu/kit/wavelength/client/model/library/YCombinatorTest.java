package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class YCombinatorTest {

	private static Parser parser;
	private static YCombinator library;

	private static LambdaTerm yCombinator;

	private LambdaTerm[] terms = { yCombinator };
	private String[] names = { "Y" };

	@BeforeClass
	public static void setUp() throws ParseException {
		library = new YCombinator();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));

		yCombinator = parser.parse("\\f.(\\x.f (x x)) (\\x.f (x x))");
	}

	@Test
	public void containsNames() {
		for (String s : names) {
			assertTrue(library.containsName(s));
		}
	}

	@Test
	public void termsCorrect() {
		for (int i = 0; i < names.length; i++) {
			assertEquals(library.getTerm(names[i]), terms[i]);
		}
	}
}
