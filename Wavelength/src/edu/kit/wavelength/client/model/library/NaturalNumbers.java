package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A {@link Library} matching integer literals to church numerals
 * and providing functions for basic arithmetic operations.
 *
 * Note that the {@link ExecutionEngine} can try to
 * accelerate the calculation on a {@link LambdaTerm}
 * that uses this library.
 */
public final class NaturalNumbers implements Library {

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
		return "Natural numbers";
	}

	@Override
	public String serialize() {
		return null;
	}

}
