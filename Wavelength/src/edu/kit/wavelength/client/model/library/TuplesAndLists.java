package edu.kit.wavelength.client.model.library;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

/**
 * A {@link Library} providing operations for working with
 * tuples and lists.
 *
 */
public final class TuplesAndLists implements Library {

	// true and false needed for more advanced list operations
	private final NamedTerm tru = new NamedTerm("true", new Abstraction("t", new Abstraction("f", new BoundVariable(2))));
	private final NamedTerm fal = new NamedTerm("false", new Abstraction("t", new Abstraction("f", new BoundVariable(1))));
	
	private final NamedTerm pair = new NamedTerm("pair", new Abstraction("x", new Abstraction("y", new Abstraction("z", new Application(new Application(new BoundVariable(1), new BoundVariable(3)), new BoundVariable(2))))));
	private final NamedTerm first = new NamedTerm("first", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(2))))));
	private final NamedTerm second = new NamedTerm("second", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(1))))));
	
	// Build a list where each list node is pair (a,b). 
	// Where 'a' is the head of the list and 'b' the tail (possibly containing more pairs).
	// A List containing only one Element 'a' should be constructed by: '(prepend a newList)'
	// same as 'false'
	private final NamedTerm newList = new NamedTerm("newList", new Abstraction("t", new Abstraction("f", new BoundVariable(1))));
	// same as 'pair'
	private final NamedTerm prepend = new NamedTerm("prepend", new Abstraction("x", new Abstraction("y", new Abstraction("z", new Application(new Application(new BoundVariable(1), new BoundVariable(3)), new BoundVariable(2))))));
	// same as 'first'
	private final NamedTerm head = new NamedTerm("head", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(2))))));
	// same as 'second'
	private final NamedTerm tail = new NamedTerm("tail", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(1))))));
	private final NamedTerm isEmpty = new NamedTerm("isEmpty", new Abstraction("l", new Application(new Application(new BoundVariable(1), new Abstraction("h", new Abstraction("t", new Abstraction("d", fal)))), tru)));
	
	private final List<NamedTerm> definitions = Arrays.asList(tru, fal, pair, first, second, newList, prepend, head, tail, isEmpty);
	
	public static final char ID = 't';
	
	@Override
	public LambdaTerm getTerm(String name) {
		for(NamedTerm t: definitions) {
			if(t.getName().equals(name)) {
				return t.getInner();
			}
		}
		return null;
	}

	@Override
	public boolean containsName(String name) {
		for(NamedTerm t: definitions) {
			if(t.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "Tuples and Lists";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}

}
