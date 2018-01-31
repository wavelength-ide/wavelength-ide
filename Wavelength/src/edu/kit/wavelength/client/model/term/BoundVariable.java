package edu.kit.wavelength.client.model.term;

/**
 * Represents a bound variable in the untyped lambda calculus.
 * 
 * It refers to its corresponding {@link Abstraction} using its
 * De Bruijn index.
 *
 */
public final class BoundVariable implements LambdaTerm {
	
	private int deBruijnIndex;

	/**
	 * Creates a new bound variable term.
	 * @param deBruijnIndex The De Bruijn index of the term
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
	 * @return The De Bruijn index
	 */
	public int getDeBruijnIndex() {
		return deBruijnIndex;
	}
	
	@Override
	public boolean equals(Object other) {
		return false;
	}

	@Override
	public String serialize() {
		return null;
	}

}
