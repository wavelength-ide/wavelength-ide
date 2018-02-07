package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

/**
 * A {@link Library} containing the Y combinator.
 *
 */
public final class YCombinator implements Library {

	// naisa code
	private final NamedTerm yCombinator = new NamedTerm("Y",
			new Abstraction("f",
					new Application(
							new Abstraction("x",
									new Application(new BoundVariable(2),
											new Application(new BoundVariable(1), new BoundVariable(1)))),
							new Abstraction("x", new Application(new BoundVariable(2),
									new Application(new BoundVariable(1), new BoundVariable(1)))))));
	
	public static final char ID = 'Y';

	@Override
	public LambdaTerm getTerm(String name) {
		if(yCombinator.getName().equals(name)) {
			return yCombinator.getInner();
		}
		return null;
	}

	@Override
	public boolean containsName(String name) {
		if(yCombinator.getName().equals(name)) {
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Y-Combinator";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}
}
