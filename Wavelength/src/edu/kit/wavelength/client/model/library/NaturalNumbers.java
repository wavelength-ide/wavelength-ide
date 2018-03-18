package edu.kit.wavelength.client.model.library;

import java.util.Arrays;
import java.util.List;

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

	public static final char ID = 'n';

	/**
	 * Creates a new NaturalNumbers library.
	 * 
	 * @param turbo
	 *            {@link true} if calculations on this Library should be accelerated
	 *            and {@link false} otherwise
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
			try {
				return LambdaTerm.churchNumber(Integer.valueOf(name));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Number too large: " + name);
			}
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
		if (name == null) {
			return false;
		}
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
		return new StringBuilder(ID + (turbo ? "t" : "f"));
	}

	public static NaturalNumbers fromSerialized(String serialized) {
		return new NaturalNumbers("t".equals(serialized));
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		NaturalNumbers naturalNumbers = (NaturalNumbers) o;
		return this.turbo == naturalNumbers.turbo;
	}
	
	@Override
	public List<TermInfo> getTermInfos() {
		return Arrays.asList(new TermInfo("<number>", "Allows you to type any natural number as an integer literal."),
		                     new TermInfo("plus a b", "'plus a b' yields a + b for a, b : nat."),
		                     new TermInfo("minus a b", "'minus a b' yields a - b for a, b : nat."),
		                     new TermInfo("times a b", "'times a b' yields a * b for a, b : nat."),
		                     new TermInfo("pow a b", "'pow a b' yields a^b for a, b : nat."),
		                     new TermInfo("pred a", "'pred a' yields a - 1 for a : nat."),
		                     new TermInfo("succ a", "'succ a' yields a + 1 for a : nat."));
	}
	
}
