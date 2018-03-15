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

public class BooleanTest {

	private static Parser parser;
	private static Boolean library;

	/**
	 * Initializes the Library and the Parser.
	 */
	@Before
	public void setUp() {
		library = new Boolean();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));
	}

	@Test
	public void containsNames() {
		String[] names = { "true", "false", "and", "or", "not", "ifThenElse" };

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
		LambdaTerm and = parser.parse("\\p.\\q.p q p");
		LambdaTerm or = parser.parse("\\p.\\q.p p q");
		LambdaTerm not = parser.parse("\\p.p false true");
		LambdaTerm ifThenElse = parser.parse("\\p.\\a.\\b.p a b");

		LambdaTerm[] terms = { tru, fal, and, or, not, ifThenElse };
		String[] names = { "true", "false", "and", "or", "not", "ifThenElse" };

		assertEquals(terms.length, names.length);
		for (int i = 0; i < names.length; i++) {
			assertEquals(library.getTerm(names[i]), terms[i]);
		}
	}

	@Test
	public void simpelTermsFunctionality() throws ParseException {

		assertEquals(LibraryTestUltilities.reduce("and true true", Arrays.asList(library)), parser.parse("true"));
		assertEquals(LibraryTestUltilities.reduce("and true false", Arrays.asList(library)), parser.parse("false"));
		assertEquals(LibraryTestUltilities.reduce("and false true", Arrays.asList(library)), parser.parse("false"));
		assertEquals(LibraryTestUltilities.reduce("and false false", Arrays.asList(library)), parser.parse("false"));

		assertEquals(LibraryTestUltilities.reduce("or true true", Arrays.asList(library)), parser.parse("true"));
		assertEquals(LibraryTestUltilities.reduce("or true false", Arrays.asList(library)), parser.parse("true"));
		assertEquals(LibraryTestUltilities.reduce("or false true", Arrays.asList(library)), parser.parse("true"));
		assertEquals(LibraryTestUltilities.reduce("or false false", Arrays.asList(library)), parser.parse("false"));

		assertEquals(LibraryTestUltilities.reduce("not true", Arrays.asList(library)), parser.parse("false"));
		assertEquals(LibraryTestUltilities.reduce("not false", Arrays.asList(library)), parser.parse("true"));

		assertEquals(LibraryTestUltilities.reduce("ifThenElse true a b", Arrays.asList(library)), parser.parse("a"));
		assertEquals(LibraryTestUltilities.reduce("ifThenElse false a b", Arrays.asList(library)), parser.parse("b"));
	}
}
