package edu.kit.wavelength.client.model.terms;

/**
 * Represents an application in the untyped lambda calculus.
 * 
 * An application has a left hand side and a right hand side,
 * both of which may be arbitrary lambda terms.
 *
 */
public class Application implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the left hand side of the application.
	 * @return The left hand side of the application
	 */
	public LambdaTerm getLeftHandSide() {
		return null;
	}
	
	/**
	 * Returns the right hand side of the application.
	 * @return The right hand side of the application
	 */
	public LambdaTerm getRightHandSide() {
		return null;
	}

}
