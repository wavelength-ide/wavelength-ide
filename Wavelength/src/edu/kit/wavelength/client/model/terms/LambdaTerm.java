package edu.kit.wavelength.client.model.terms;

/**
 * Represents a term in the untyped lambda calculus.
 */
public interface LambdaTerm {
	/**
	 * Accept a visitor by invoking the correct visit* method.
	 * @param The visitor whose correct visit* method should be invoked
	 * @return The return value of the invoked visit* method
	 */
	public <T> T acceptVisitor(Visitor<T> v);
}
