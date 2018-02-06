package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;

/**
 * A {@link Library} matching integer literals to church numerals and providing
 * functions for basic arithmetic operations.
 *
 * Note that the {@link ExecutionEngine} can try to accelerate the calculation
 * on a {@link LambdaTerm} that uses this library.
 */
public final class NaturalNumbers implements Library {

	private final String decimalRegex = "[1-9][0-9]*|0";
	private final PartialApplication[] acceleratable = new PartialApplication[] { new PartialApplication.Addition(),
			new PartialApplication.Subtraction(), new PartialApplication.Exponentiation(),
			new PartialApplication.Multiplication(), new PartialApplication.Predecessor(),
			new PartialApplication.Successor() };
	private boolean turbo;

	/**
	 * Creates a new NaturalNumbers library.
	 * 
	 * @param turbo {@link true} if calculations on this Library should be accelerated and {@link false} otherwise 
	 */
	public NaturalNumbers(boolean turbo) {
		this.turbo = turbo;
	}

	@Override
	public LambdaTerm getTerm(String name) {
		if (name == null) {
			return null;
		}
		if (name.matches(decimalRegex)) {
			return LambdaTerm.churchNumber(Integer.valueOf(name));
		} else {
			for (int i = 0; i < acceleratable.length; ++i) {
				if (acceleratable[i].getName().equals(name)) {
					return turbo ? acceleratable[i] : acceleratable[i].getRepresented();
				}
			}
		}
		return null;
	}

	@Override
	public boolean containsName(String name) {
		if (name.matches(decimalRegex)) {
			return true;
		}

		for (int i = 0; i < acceleratable.length; ++i) {
			if (acceleratable[i].getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return (turbo ? "Accelerated " : "") + "Natural Numbers";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("n" + (turbo ? "t" : "f"));
	}
	
	public static NaturalNumbers fromSerialized(String serialized) {
		return new NaturalNumbers("t".equals(serialized));
	}

}
