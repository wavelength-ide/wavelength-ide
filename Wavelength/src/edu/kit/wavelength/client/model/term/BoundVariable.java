package edu.kit.wavelength.client.model.term;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;

/**
 * Represents a bound variable in the untyped lambda calculus.
 * 
 * It refers to its corresponding {@link Abstraction} using its De Bruijn index.
 *
 */
public final class BoundVariable implements LambdaTerm {

	private int deBruijnIndex;
	
	public static final char ID = 'b';

	/**
	 * Creates a new bound variable term.
	 * 
	 * @param deBruijnIndex
	 *            The De Bruijn index of the term
	 */
	public BoundVariable(int deBruijnIndex) {
		this.deBruijnIndex = deBruijnIndex;
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitBoundVariable(this);
	}

	/**
	 * Returns the De Bruijn index of the variable.
	 * 
	 * @return The De Bruijn index
	 */
	public int getDeBruijnIndex() {
		return deBruijnIndex;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof BoundVariable))
			return false;

		BoundVariable var = (BoundVariable) other;

		return var.getDeBruijnIndex() == this.getDeBruijnIndex();
	}

	@Override
	public LambdaTerm clone() {
		return new BoundVariable(deBruijnIndex);
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(ID + String.valueOf(deBruijnIndex));
	}
	
	public static BoundVariable fromSerialized(String serialized) {
		try {
			return new BoundVariable(Integer.valueOf(serialized));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException();
		}
	}

}
