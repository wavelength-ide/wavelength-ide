package edu.kit.wavelength.client.model.term;

import java.util.List;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;

/**
 * Represents an application in the untyped lambda calculus.
 * 
 * An application has a left hand side and a right hand side, both of which may
 * be arbitrary lambda terms.
 *
 */
public final class Application implements LambdaTerm {

	private LambdaTerm leftHandSide;
	private LambdaTerm rightHandSide;
	private int depth;

	private static final int LEFT_POSITION = 0;
	private static final int RIGHT_POSITION = 1;
	private static final int NUM_COMPONENTS = 2;

	public static final char ID = 'a';

	/**
	 * Creates a new application.
	 * 
	 * @param leftHandSide
	 *            The left hand side of the application
	 * @param rightHandSide
	 *            The right hand side of the application
	 */
	public Application(LambdaTerm leftHandSide, LambdaTerm rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
		this.depth = Math.max(leftHandSide.acceptVisitor(new GetDepthVisitor()),
				rightHandSide.acceptVisitor(new GetDepthVisitor())) + 1;
		
		if (this.depth > LambdaTerm.MAX_DEPTH)
			throw new TermTooDeepException();
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitApplication(this);
	}

	/**
	 * Returns the left hand side of the application.
	 * 
	 * @return The left hand side of the application
	 */
	public LambdaTerm getLeftHandSide() {
		return leftHandSide;
	}

	/**
	 * Returns the right hand side of the application.
	 * 
	 * @return The right hand side of the application
	 */
	public LambdaTerm getRightHandSide() {
		return rightHandSide;
	}
	
	/**
	 * Returns the depth of this application.
	 * @return The depth of this application
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Application))
			return false;

		Application app = (Application) other;

		return app.getLeftHandSide().equals(this.getLeftHandSide())
				&& app.getRightHandSide().equals(this.getRightHandSide());
	}

	@Override
	public LambdaTerm clone() {
		return new Application(leftHandSide.clone(), rightHandSide.clone());
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID)
				.append(SerializationUtilities.enclose(leftHandSide.serialize(), rightHandSide.serialize()));
	}

	/**
	 * Restores an application from its serialization.
	 * 
	 * @param serialized
	 *            A serialized application
	 * @return The restored application
	 */
	public static Application fromSerialized(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == NUM_COMPONENTS;
		return new Application(LambdaTerm.deserialize(extracted.get(LEFT_POSITION)),
				LambdaTerm.deserialize(extracted.get(RIGHT_POSITION)));
	}

}
