package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A {@link Library} containing the Y combinator.
 *
 */
public final class YCombinator implements Library {

	@Override
	public LambdaTerm getTerm(String name) {
		return null;
	}

	@Override
	public boolean containsName(String name) {
		return false;
	}

	@Override
	public String getName() {
		return "Y-Combinator";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("y");
	}

}
