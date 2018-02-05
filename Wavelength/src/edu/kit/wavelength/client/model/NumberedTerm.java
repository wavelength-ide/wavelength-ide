package edu.kit.wavelength.client.model;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;

class NumberedTerm implements Serializable {
	private LambdaTerm term;
	private int number;

	public NumberedTerm(LambdaTerm term, int number) {
		Objects.requireNonNull(term);

		this.term = term;
		this.number = number;
	}
	
	public NumberedTerm(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == 2;
		
		this.number = Integer.valueOf(extracted.get(0));
		this.term = LambdaTerm.deserialize(extracted.get(1));
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
