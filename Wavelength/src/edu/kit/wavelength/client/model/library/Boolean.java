package edu.kit.wavelength.client.model.library;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

/**
 * This {@link Library} contains {@link LambdaTerm} for the boolean values true and false.
 *
 */
public final class Boolean implements Library {
	
	private final NamedTerm tru = new NamedTerm("true", new Abstraction("t", new Abstraction("f", new BoundVariable(2))));
	private final NamedTerm fal = new NamedTerm("false", new Abstraction("t", new Abstraction("f", new BoundVariable(1))));
	private final NamedTerm and = new NamedTerm("and", new Abstraction("p", new Abstraction("q", new Application(new Application(new BoundVariable(2), new BoundVariable(1)), new BoundVariable(2)))));
	private final NamedTerm or = new NamedTerm("or", new Abstraction("p", new Abstraction("q", new Application(new Application(new BoundVariable(2), new BoundVariable(2)), new BoundVariable(1)))));
	// a correct implementation of not is dependent on the current evaluation strategy. 
	//This implementation only works with normal order.
	private final NamedTerm not = new NamedTerm("not", new Abstraction("p", new Application(new Application(new BoundVariable(1), fal), tru)));
	private final NamedTerm ifThenElse = new NamedTerm("ifThenElse", new Abstraction("p", new Abstraction("a", new Abstraction("b", new Application(new Application(new BoundVariable(3), new BoundVariable(2)), new BoundVariable(1))))));;
	
	private final List<NamedTerm> definitions = Arrays.asList(tru, fal, and, or, not, ifThenElse);
	
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
		return "CurchBoolean";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("b");
	}
}
