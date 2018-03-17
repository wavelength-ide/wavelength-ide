package edu.kit.wavelength.client.model.term;

import java.util.List;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;

/**
 * Represents an abstraction in the untyped lambda calculus.
 * 
 * An abstraction has an inner term and a preferred name for the variable it
 * abstracts. When displaying the abstraction, a different name may be used,
 * since terms referring to this abstraction will use De Bruijn indices to do
 * so.
 *
 */
public final class Abstraction implements LambdaTerm {

	private String preferredName;
	private LambdaTerm inner;
	private int depth;

	private static final int NAME_POSITION = 0;
	private static final int INNER_POSITION = 1;
	private static final int NUM_COMPONENTS = 2;

	public static final char ID = 'A';

	/**
	 * Creates a new abstraction.
	 * 
	 * @param preferredName
	 *            The preferred name for the variable that is abstracted
	 * @param inner
	 *            The lambda term that the abstraction encloses
	 */
	public Abstraction(String preferredName, LambdaTerm inner) {
		this.preferredName = preferredName;
		this.inner = inner;
		this.depth = inner.acceptVisitor(new GetDepthVisitor()) + 1;
		
		if (this.depth > LambdaTerm.MAX_DEPTH)
			throw new TermTooDeepException();
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitAbstraction(this);
	}

	/**
	 * Gets the preferred name for the abstracted variable.
	 * 
	 * @return The preferred name
	 */
	public String getPreferredName() {
		return preferredName;
	}

	/**
	 * Gets the inner term of the abstraction.
	 * 
	 * @return The term that this abstraction encloses
	 */
	public LambdaTerm getInner() {
		return inner;
	}
	
	/**
	 * Returns the depth of this abstraction.
	 * @return The depth of this abstraction
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Abstraction))
			return false;

		Abstraction abs = (Abstraction) other;

		return abs.getPreferredName().equals(this.getPreferredName()) && abs.getInner().equals(this.getInner());
	}

	@Override
	public LambdaTerm clone() {
		return new Abstraction(preferredName, inner.clone());
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID)
				.append(SerializationUtilities.enclose(new StringBuilder(preferredName), inner.serialize()));
	}

	/**
	 * Restores an abstraction from its serialization.
	 * 
	 * @param serialized
	 *            A serialized abstraction
	 * @return The abstraction described by the serialization
	 */
	public static Abstraction fromSerialized(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == NUM_COMPONENTS;
		return new Abstraction(extracted.get(NAME_POSITION), LambdaTerm.deserialize(extracted.get(INNER_POSITION)));
	}

}
