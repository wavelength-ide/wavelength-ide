package edu.kit.wavelength.client.model;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A simple tuple class consisting of a LambdaTerm and a number.
 *
 */
class NumberedTerm implements Serializable {
	private LambdaTerm term;
	private int number;
	
	private static final int NUMBER_POSITION = 0;
	private static final int TERM_POSITION = 1;
	private static final int NUM_COMPONENTS = 2;

	/**
	 * Creates a new numbered term.
	 * @param term The lambda term
	 * @param number The number
	 */
	public NumberedTerm(LambdaTerm term, int number) {
		Objects.requireNonNull(term);

		this.term = term;
		this.number = number;
	}
	
	/**
	 * Creates a new numbered term from serialized numbered term.
	 * @param serialized The serialized numbered term
	 */
	public NumberedTerm(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == NUM_COMPONENTS;
		
		this.number = Integer.valueOf(extracted.get(NUMBER_POSITION));
		this.term = LambdaTerm.deserialize(extracted.get(TERM_POSITION));
	}

	/**
	 * Returns the term.
	 * @return The term
	 */
	public LambdaTerm getTerm() {
		return term;
	}

	/**
	 * Returns the number.
	 * @return The number
	 */
	public int getNumber() {
		return number;
	}

	@Override
	public StringBuilder serialize() {
		return SerializationUtilities.enclose(new StringBuilder(String.valueOf(number)), term.serialize());
	}
}
