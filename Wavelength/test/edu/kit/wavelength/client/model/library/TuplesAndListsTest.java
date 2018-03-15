package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class TuplesAndListsTest {

	private static Parser parser;
	private static TuplesAndLists library;

	/**
	 * Initializes the Library and the Parser.
	 */
	@Before
	public void setUp() throws ParseException {
		library = new TuplesAndLists();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));
	}

	@Test
	public void containsNames() {
		String[] names = { "true", "false", "pair", "first", "second", "newList", "prepend", "head", "tail",
				"isEmpty" };

		for (String s : names) {
			assertTrue(library.containsName(s));
		}
	}

	@Test
	public void termsCorrect() throws ParseException {
		CustomLibrary customLibrary = new CustomLibrary("");
		customLibrary.addTerm(new Abstraction("t", new Abstraction("f", new BoundVariable(2))), "true");
		customLibrary.addTerm(new Abstraction("t", new Abstraction("f", new BoundVariable(1))), "false");
		parser = new Parser(new ArrayList<Library>(Arrays.asList(customLibrary)));

		LambdaTerm tru = parser.parse("\\t.\\f.t");
		LambdaTerm fal = parser.parse("\\t.\\f.f");
		LambdaTerm pair = parser.parse("\\x.\\y.\\z.z x y");
		LambdaTerm first = parser.parse("\\p.p (\\x.\\y.x)");
		LambdaTerm second = parser.parse("\\p.p (\\x.\\y.y)");
		LambdaTerm newList = parser.parse("\\t.\\f.f");
		LambdaTerm prepend = parser.parse("\\x.\\y.\\z.z x y");
		LambdaTerm head = parser.parse("\\p.p (\\x.\\y.x)");
		LambdaTerm tail = parser.parse("\\p.p (\\x.\\y.y)");
		LambdaTerm isEmpty = parser.parse("\\l.l (\\h.\\t.\\d.false) true");

		LambdaTerm[] terms = { tru, fal, pair, first, second, newList, prepend, head, tail, isEmpty };
		String[] names = { "true", "false", "pair", "first", "second", "newList", "prepend", "head", "tail",
				"isEmpty" };

		assertEquals(terms.length, names.length);
		for (int i = 0; i < names.length; i++) {
			assertEquals(library.getTerm(names[i]), terms[i]);
		}
	}

	@Test
	public void simpleTermsFunctionality() throws ParseException {

		assertEquals(LibraryTestUltilities.reduce("first (pair a b)", Arrays.asList(library)), parser.parse("a"));
		assertEquals(LibraryTestUltilities.reduce("second (pair a b)", Arrays.asList(library)), parser.parse("b"));

		assertEquals(LibraryTestUltilities.reduce("isEmpty newList", Arrays.asList(library)), parser.parse("true"));
		assertEquals(LibraryTestUltilities.reduce("head (prepend a newList)", Arrays.asList(library)),
				parser.parse("a"));
		assertEquals(LibraryTestUltilities.reduce("head (prepend b (prepend a newList))", Arrays.asList(library)),
				parser.parse("b"));
		assertEquals(LibraryTestUltilities.reduce("head (tail (prepend b(prepend a newList)))", Arrays.asList(library)),
				parser.parse("a"));
		assertEquals(LibraryTestUltilities.reduce("isEmpty (tail (tail (prepend b(prepend a newList))))",
				Arrays.asList(library)), parser.parse("true"));
	}
}
