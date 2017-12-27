package edu.kit.wavelength.client.model.terms;

/**
 * Represents a bound variable in the untyped lambda calculus.
 * 
 * It refers to its corresponding abstraction using its
 * De Bruijn index.
 *
 */
public class BoundVariable implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the De Bruijn index of the variable.
	 * @return The De Bruihn index
	 */
	public int getDeBruijnIndex() {
		return 0;
	}

}
