package edu.kit.wavelength.client.model.term;

/**
 * Represents an abstraction in the untyped lambda calculus.
 * 
 * An abstraction has an inner term and a preferred name for the
 * variable it abstracts. When displaying the abstraction, a different
 * name may be used, since terms referring to this abstraction
 * will use De Bruijn indices to do so.
 *
 */
public final class Abstraction implements LambdaTerm {
	
	private String preferredName;
	private LambdaTerm inner;
	
	/**
	 * Creates a new abstraction.
	 * @param preferredName The preferred name for the variable that is abstracted
	 * @param inner The lambda term that the abstraction encloses
	 */
	public Abstraction(String preferredName, LambdaTerm inner) {
		this.preferredName = preferredName;
		this.inner = inner;
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitAbstraction(this);
	}
	
	/**
	 * Gets the preferred name for the abstracted variable.
	 * @return The preferred name
	 */
	public String getPreferredName()
	{
		return preferredName;
	}
	
	/**
	 * Gets the inner term of the abstraction.
	 * @return The term that this abstraction encloses
	 */
	public LambdaTerm getInner() {
		return inner;
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
