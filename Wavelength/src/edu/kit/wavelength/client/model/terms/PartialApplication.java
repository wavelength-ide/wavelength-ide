package edu.kit.wavelength.client.model.terms;

import java.util.List;

/**
 * Represents a term that consists of a library function that may be accelerated, as well as
 * zero or more applications with arguments for said library function.
 * @author markus
 *
 */
public class PartialApplication implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	/**
	 * Returns the lambda term that this partial application represents.
	 * @return The lambda term that this partial application represents
	 */
	public LambdaTerm getRepresented() {
		return null;
	}
	
	/**
	 * Returns the name of the library function for the partial application.
	 * @return The name of the library function for the partial application
	 */
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
	 * @param nextParam The parameter to be accepted
	 * @return A lambda term for the partial application with the new parameter as described above
	 */
	public LambdaTerm accept(LambdaTerm nextParam) {
		return null;
	}
	
}
