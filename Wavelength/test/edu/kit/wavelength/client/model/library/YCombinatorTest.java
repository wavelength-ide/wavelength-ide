package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class YCombinatorTest {

	private static Parser parser;
	private static YCombinator library;

	/**
	 * Initializes the Library and the Parser.
	 */
	@Before
	public void setUp() throws ParseException {
		library = new YCombinator();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));
	}

	@Test
	public void containsNames() {
		String[] names = { "Y" };

		for (String s : names) {
			assertTrue(library.containsName(s));
		}
	}

	@Test
	public void termsCorrect() throws ParseException {
		parser = new Parser(new ArrayList<Library>());
		LambdaTerm yCombinator = parser.parse("\\f.(\\x.f (x x)) (\\x.f (x x))");

		LambdaTerm[] terms = { yCombinator };
		String[] names = { "Y" };

		assertEquals(terms.length, names.length);
		for (int i = 0; i < names.length; i++) {
			assertEquals(library.getTerm(names[i]), terms[i]);
		}
	}
	
	//TODO einfache Test für den Y Kombinator?
}
