package edu.kit.wavelength.client.model.term;

import edu.kit.wavelength.client.model.serialization.Serializable;

/**
 * Represents a term in the untyped lambda calculus.
 */
public interface LambdaTerm extends Serializable {
	
	/**
	 * Creates a lambda term from its serialization.
	 * @param serialized The serialized representation of the lambda term
	 * @return A lambda term that is equal to the term that was serialized
	 */
	public static LambdaTerm deserialize(String serialized) {
		return null;
	}
	
	/**
	 * Accept a visitor by invoking the correct visit* method.
	 * @param The visitor whose correct visit* method should be invoked
	 * @return The return value of the invoked visit* method
	 */
	public <T> T acceptVisitor(Visitor<T> v);
}
