package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A library containing the Y combinator.
 *
 */
final class YCombinator implements Library {

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
		return null;
	}

	@Override
	public String serialize() {
		return null;
	}

}
