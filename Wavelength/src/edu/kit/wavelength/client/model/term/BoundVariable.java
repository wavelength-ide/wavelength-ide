package edu.kit.wavelength.client.model.term;

/**
 * Represents a bound variable in the untyped lambda calculus.
 * 
 * It refers to its corresponding abstraction using its
 * De Bruijn index.
 *
 */
public final class BoundVariable implements LambdaTerm {

	/**
	 * Creates a new bound variable term.
	 * @param deBruijnIndex The De Bruijn index of the term
	 */
	public BoundVariable(int deBruijnIndex) {
		
	}
	
	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the De Bruijn index of the variable.
	 * @return The De Bruijn index
	 */
	public int getDeBruijnIndex() {
		return 0;
	}
	
	@Override
	public boolean equals(Object other) {
		return false;
	}

}
