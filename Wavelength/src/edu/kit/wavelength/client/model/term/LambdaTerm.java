package edu.kit.wavelength.client.model.term;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.term.PartialApplication.Addition;
import edu.kit.wavelength.client.model.term.PartialApplication.Exponentiation;
import edu.kit.wavelength.client.model.term.PartialApplication.Multiplication;
import edu.kit.wavelength.client.model.term.PartialApplication.Predecessor;
import edu.kit.wavelength.client.model.term.PartialApplication.Subtraction;
import edu.kit.wavelength.client.model.term.PartialApplication.Successor;

/**
 * Represents a term in the untyped lambda calculus.
 */
public interface LambdaTerm extends Serializable {
	
	/**
	 * The maximal depth of any lambda term
	 */
	public static final int MAX_SIZE = 3500;
	
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
		case Abstraction.ID:
			return Abstraction.fromSerialized(stripped);
		
		case Application.ID:
			return Application.fromSerialized(stripped);
			
		case BoundVariable.ID:
			return BoundVariable.fromSerialized(stripped);
			
		case FreeVariable.ID:
			return FreeVariable.fromSerialized(stripped);
			
		case NamedTerm.ID:
			return NamedTerm.fromSerialized(stripped);
			
		case Addition.ID:
			return Addition.fromSerialized(stripped);
			
		case Subtraction.ID:
			return Subtraction.fromSerialized(stripped);
			
		case Successor.ID:
			return Successor.fromSerialized(stripped);
			
		case Predecessor.ID:
			return Predecessor.fromSerialized(stripped);
			
		case Multiplication.ID:
			return Multiplication.fromSerialized(stripped);
			
		case Exponentiation.ID:
			return Exponentiation.fromSerialized(stripped);
		}
		
		throw new IllegalArgumentException("serialized must represent a lambda term");
	}
	
	/**
	 * Returns a named lambda term that corresponds to a church number.
	 * @param value The value of the church number to construct
	 * @return The requested church number
	 */
	public static LambdaTerm churchNumber(int value) {
		if (value < 0)
			throw new IllegalArgumentException("value must be non-negative");

		LambdaTerm inner = new BoundVariable(1);
		for (int i = 1; i <= value; ++i) {
			inner = new Application(new BoundVariable(2), inner);
		}
		return new NamedTerm(String.valueOf(value), new Abstraction("s", new Abstraction("z", inner)));
	}

	/**
	 * Creates a deep clone of the lambda term.
	 * @return A deep clone
	 */
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
