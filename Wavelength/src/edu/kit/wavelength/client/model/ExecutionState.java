package edu.kit.wavelength.client.model;

import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

public class ExecutionState implements Serializable {

	public ExecutionState(ReductionOrder reduction, OutputSize size, LambdaTerm[] terms) {
		
	}
	
	/**
	 * Serializes the ExecutionState by serializing its components.
	 * @return A
	 */
	@Override
	public String serialize() {
		return null;
	}
	
	public LambdaTerm[] getTerms() {
		return null;
	}

	@Override
	public void deserialize(String serialized) {
		// TODO soll das wirklich hier sein?
	}
}
