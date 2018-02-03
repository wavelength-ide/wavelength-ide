package edu.kit.wavelength.client.model.term;

import edu.kit.wavelength.client.model.serialization.Serializable;

/**
 * Represents a term in the untyped lambda calculus.
 */
public interface LambdaTerm extends Serializable {

	//private final char ABSTRACTION_ID = 'A';
	//private final char APPLICATION_ID = 'a';
	
	/**
	 * Creates a lambda term from its serialization.
	 * 
	 * @param serialized
	 *            The serialized representation of the lambda term
	 * @return A lambda term that is equal to the term that was serialized
	 */
	public static LambdaTerm deserialize(String serialized) {
		if (serialized == null || serialized.isEmpty())
			throw new IllegalArgumentException("serialized must be non-empty");
		
		String stripped = serialized.substring(1);
		
		switch (serialized.charAt(0)) {
		case 'A':
			return Abstraction.fromSerialized(stripped);
		
		case 'a':
			return Application.fromSerialized(stripped);
			
		case 'b':
			return BoundVariable.fromSerialized(stripped);
			
		case 'f':
			return FreeVariable.fromSerialized(stripped);
			
		case 'n':
			return NamedTerm.fromSerialized(stripped);
		}
		
		throw new IllegalArgumentException("serialized must represent a lambda term");
	}

	public LambdaTerm clone();

	/**
	 * Accept a {@link Visitor} by invoking the correct visit* method.
	 * 
	 * @param v
	 *            The {@link Visitor} whose correct visit* method should be invoked
	 * @return The return value of the invoked visit* method
	 */
	public <T> T acceptVisitor(Visitor<T> v);
}
