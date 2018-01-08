package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This library contains lambda terms for the boolean values true and false.
 *
 */
public final class Boolean implements Library {

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
