package edu.kit.wavelength.client.model.library;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.parsing.Parser;

/**
 * A {@link Library} contains definitions for {@link LambdaTerms}s for tuples
 * (pairs) and lists.
 * 
 * The terms are encoded by the Church encoding. The library contains tuples and
 * the simple operations 'first' and 'second'. In addition this library supports
 * lists. A list node is represented by a pair. The library also supports basic
 * operations on lists.
 */
public final class TuplesAndLists implements Library {

	public static final char ID = 't';
	public static final String NAME = "Church Tuples and Lists";

	// true and false needed for more advanced list operations
	private final NamedTerm tru = new NamedTerm("true",
			new Abstraction("t", new Abstraction("f", new BoundVariable(2))));
	private final NamedTerm fal = new NamedTerm("false",
			new Abstraction("t", new Abstraction("f", new BoundVariable(1))));

	// library terms for tuples
	private final NamedTerm pair = new NamedTerm("pair", new Abstraction("x", new Abstraction("y", new Abstraction("z",
			new Application(new Application(new BoundVariable(1), new BoundVariable(3)), new BoundVariable(2))))));
	private final NamedTerm first = new NamedTerm("first", new Abstraction("p",
			new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(2))))));
	private final NamedTerm second = new NamedTerm("second", new Abstraction("p",
			new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(1))))));

	// Build a list where each list node is pair (a,b).
	// Where 'a' is the head of the list and 'b' the tail (possibly containing
	// more pairs).
	// A List containing only one Element 'a' should be constructed by:
	// '(cons a nil)'
	// same as 'false'
	private final NamedTerm nil = new NamedTerm("nil",
			new Abstraction("t", new Abstraction("f", new BoundVariable(1))));
	// same as 'pair'
	private final NamedTerm cons = new NamedTerm("cons",
			new Abstraction("x", new Abstraction("y",
					new Abstraction("z", new Application(new Application(new BoundVariable(1), new BoundVariable(3)),
							new BoundVariable(2))))));
	// same as 'first'
	private final NamedTerm head = new NamedTerm("head", new Abstraction("p",
			new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(2))))));
	// \l. l (\a b c. b) nil
	private final NamedTerm tail = new NamedTerm("tail", Parser.parseUnresolved("λl. l (λa. λb. λc. b) (λc. λn. n)"));
	private final NamedTerm isEmpty = new NamedTerm("isEmpty",
			new Abstraction("l", new Application(new Application(new BoundVariable(1),
					new Abstraction("h", new Abstraction("t", new Abstraction("d", fal)))), tru)));

	private final List<NamedTerm> definitions = Arrays.asList(tru, fal, pair, first, second, nil, cons, head,
			tail, isEmpty);

	@Override
	public LambdaTerm getTerm(String name) {
		for (NamedTerm t : definitions) {
			if (t.getName().equals(name)) {
				return t.getInner();
			}
		}
		return null;
	}

	@Override
	public boolean containsName(String name) {
		for (NamedTerm t : definitions) {
			if (t.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}

	@Override
	public List<TermInfo> getTermInfos() {
		return Arrays.asList(new TermInfo("true", "literal or 'true a b' yields a"),
				new TermInfo("false", "literal or 'false a b' yields b"),
				new TermInfo("pair a b", "(a, b)"),
				new TermInfo("nil", "[]"),
				new TermInfo("first a", "a[0] for a : pair or a : list"),
				new TermInfo("second a", "a[1] for a : pair or a[1:end] for a : list"),
				new TermInfo("cons a b", "a:b for a : list"),
				new TermInfo("head a", "a[0] for a : list or a : pair"),
				new TermInfo("tail a", "a[1:end] for a : list or a[1] for a : pair"),
				new TermInfo("isEmpty a", "true if a == [] or false if a != [] for a : list"));
	}

}
