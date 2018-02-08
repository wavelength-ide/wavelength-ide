package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;


/**
 * A {@link Library} containing a definition of the Y combinator.
 */
public final class YCombinator implements Library {

	public static final char ID = 'Y';
	public static final String NAME = "Y-Combinator";
	
	private final NamedTerm yCombinator = new NamedTerm("Y",
			new Abstraction("f",
					new Application(
							new Abstraction("x",
									new Application(new BoundVariable(2),
											new Application(new BoundVariable(1), new BoundVariable(1)))),
							new Abstraction("x", new Application(new BoundVariable(2),
									new Application(new BoundVariable(1), new BoundVariable(1)))))));
	

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
		return NAME;
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(ID);
	}
}
