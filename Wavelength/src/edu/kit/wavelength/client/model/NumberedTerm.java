package edu.kit.wavelength.client.model;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;

// Simple tuple class for a lambda term and a number.
public class NumberedTerm implements Serializable {
	private LambdaTerm term;
	private int number;
	
	private static final int NUMBER_POSITION = 0;
	private static final int TERM_POSITION = 1;
	private static final int NUM_COMPONENTS = 2;

	public NumberedTerm(LambdaTerm term, int number) {
		Objects.requireNonNull(term);

		this.term = term;
		this.number = number;
	}
	
	public NumberedTerm(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == NUM_COMPONENTS;
		
		this.number = Integer.valueOf(extracted.get(NUMBER_POSITION));
		this.term = LambdaTerm.deserialize(extracted.get(TERM_POSITION));
	}

	public LambdaTerm getTerm() {
		return term;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public StringBuilder serialize() {
		return SerializationUtilities.enclose(new StringBuilder(String.valueOf(number)), term.serialize());
	}
}
