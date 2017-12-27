package edu.kit.wavelength.client.model.terms;

/**
 * Represents an abstraction in the untyped lambda calculus.
 * 
 * An abstraction has an inner term and a preferred name for the
 * variable it abstracts. When displaying the abstraction, a different
 * name may be used, since for terms referring to this abstraction
 * will use De Bruijn indices to do so.
 *
 */
public class Abstraction implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Gets the preferred name for the abstracted variable.
	 * @return The preferred name
	 */
	public String getPreferredName()
	{
		return null;
	}
	
	/**
	 * Gets the inner term of the abstraction.
	 * @return The term that this abstraction encloses
	 */
	public LambdaTerm getInner() {
		return null;
	}

}
