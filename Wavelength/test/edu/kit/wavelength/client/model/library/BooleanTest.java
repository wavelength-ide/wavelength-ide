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

public class BooleanTest {

	private static Parser parser;
	private static Boolean library;
	
	
	

	private static LambdaTerm tru;
	private static LambdaTerm fal;
	private static LambdaTerm and;
	private static LambdaTerm or;
	private static LambdaTerm not;
	private static LambdaTerm ifThenElse;

	private LambdaTerm[] terms = { tru, fal, and, or, not, ifThenElse };
	private String[] names = { "true", "false", "and", "or", "not", "ifThenElse" };

	@BeforeClass
	public static void setUp() throws ParseException {
		library = new Boolean();
		parser = new Parser(new ArrayList<Library>(Arrays.asList(library)));

		tru = parser.parse("\\t.\\f.t");
		fal = parser.parse("\\t.\\f.f");
		and = parser.parse("\\p.\\q.p q p");
		or = parser.parse("\\p.\\q.p p q");
		not = parser.parse("\\p.p false true");
		ifThenElse = parser.parse("\\p.\\a.\\b.p a b");
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
