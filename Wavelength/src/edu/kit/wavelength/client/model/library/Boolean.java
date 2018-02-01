package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This {@link Library} contains {@link LambdaTerm} for the boolean values true and false.
 *
 */
public final class Boolean implements Library {

	String[] 
	
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
		return "CurchBoolean";
	}

	@Override
	public String serialize() {
		return null;
	}

}
