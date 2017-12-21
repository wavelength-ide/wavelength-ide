package edu.kit.wavelength.client.model.terms;

import java.util.List;

public class PartialApplication implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the lambda term that this partial application represents.
	 * @return
	 */
	public LambdaTerm getRepresented() {
		return null;
	}
	
	public String getName() {
		return null;
	}

	/**
	 * Accepts a new parameter for the partial application.
	 * 
	 * If the parameter does not match the format that can be accelerated, returns a new term representing
	 * the unaccelerated application.
	 * 
	 * If the parameter matches the format that can be accelerated, returns the result of the operation
	 * represented by the partial application if all parameters are now present, or a new PartialApplication
	 * representing the partial application including the given parameter.
	 * 
	 * @param nextParam
	 * @return 
	 */
	public LambdaTerm accept(LambdaTerm nextParam) {
		return null;
	}
	
}
