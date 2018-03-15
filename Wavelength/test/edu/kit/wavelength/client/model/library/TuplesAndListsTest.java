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

public class TuplesAndListsTest {

	private static Parser parser;
	private static TuplesAndLists library;

	private static LambdaTerm tru;
	private static LambdaTerm fal;
	private static LambdaTerm pair;
	private static LambdaTerm first;
	private static LambdaTerm second;
	private static LambdaTerm newList;
	private static LambdaTerm prepend;
	private static LambdaTerm head;
	private static LambdaTerm tail;
	private static LambdaTerm isEmpty;

	private LambdaTerm[] terms = { tru, fal, pair, first, second, newList, prepend, head, tail, isEmpty };
	private String[] names = { "true", "false", "pair", "first", "second", "newList", "prepend", "head", "tail",
			"isEmpty" };

	@BeforeClass
	public static void setUp() throws ParseException {
		library = new TuplesAndLists();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));

		tru = parser.parse("\\t.\\f.t");
		fal = parser.parse("\\t.\\f.f");
		pair = parser.parse("\\x.\\y.\\z.z x y");
		first = parser.parse("\\p.p (\\x.\\y.x)");
		second = parser.parse("\\p.p (\\x.\\y.y)");
		newList = parser.parse("\\t.\\f.f");
		prepend = parser.parse("\\x.\\y.\\z.z x y");
		head = parser.parse("\\p.p (\\x.\\y.x)");
		tail = parser.parse("\\p.p (\\x.\\y.y)");
		isEmpty = parser.parse("\\l.l (\\h.\\t.\\d.false) true");
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
