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

	private final NamedTerm pair = new NamedTerm("pair", new Abstraction("x", new Abstraction("y", new Abstraction("z", new Application(new Application(new BoundVariable(1), new BoundVariable(3)), new BoundVariable(2))))));
	private final NamedTerm first = new NamedTerm("first", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(2))))));
	private final NamedTerm second = new NamedTerm("second", new Abstraction("p", new Application(new BoundVariable(1), new Abstraction("x", new Abstraction("y", new BoundVariable(1))))));
	
	private final List<NamedTerm> definitions = Arrays.asList(pair, first, second);
	
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
		return new StringBuilder("t");
	}

}
