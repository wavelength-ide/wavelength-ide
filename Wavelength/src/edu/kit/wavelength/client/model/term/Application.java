package edu.kit.wavelength.client.model.term;

/**
 * Represents an application in the untyped lambda calculus.
 * 
 * An application has a left hand side and a right hand side,
 * both of which may be arbitrary lambda terms.
 *
 */
public final class Application implements LambdaTerm {

	private LambdaTerm leftHandSide;
	private LambdaTerm rightHandSide;
	
	/**
	 * Creates a new application.
	 * @param leftHandSide The left hand side of the application
	 * @param rightHandSide The right hand side of the application
	 */
	public Application(LambdaTerm leftHandSide, LambdaTerm rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}
	
	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitApplication(this);
	}
	
	/**
	 * Returns the left hand side of the application.
	 * @return The left hand side of the application
	 */
	public LambdaTerm getLeftHandSide() {
		return leftHandSide;
	}
	
	/**
	 * Returns the right hand side of the application.
	 * @return The right hand side of the application
	 */
	public LambdaTerm getRightHandSide() {
		return rightHandSide;
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
