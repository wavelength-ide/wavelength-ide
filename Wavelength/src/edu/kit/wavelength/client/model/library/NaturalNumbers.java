package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A library matching integer literals to church numerals
 * and providing functions for basic arithmetic operations.
 *
 */
public final class NaturalNumbers implements Library{

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
