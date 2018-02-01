package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A {@link Library} providing operations for working with
 * tuples and lists.
 *
 */
public final class TuplesAndLists implements Library {

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
		return "Tuples and Lists";
	}

	@Override
	public StringBuilder serialize() {
		return null;
	}

}
