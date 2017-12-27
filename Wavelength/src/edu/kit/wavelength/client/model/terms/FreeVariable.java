package edu.kit.wavelength.client.model.terms;

/**
 * Represents a free variable in the untyped lambda calculus.
 * 
 * A free variable has a name. Unlike the name of the variable
 * bound during an abstraction, this name is not a preferred name,
 * but fixed, since it is free over the entire lambda term that
 * is being represented. Changing this name would therefore
 * change the lambda term.
 *
 */
public class FreeVariable implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the name of the free variable.
	 * @return The name of the free variable
	 */
	public String getName() {
		return null;
	}

}
