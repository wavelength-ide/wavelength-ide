package edu.kit.wavelength.client.model.term;

/**
 * Represents a {@link LambdaTerm} that consists of a library function that may be accelerated, as well as
 * zero or more applications with arguments for said library function.
 *
 */
public abstract class PartialApplication implements LambdaTerm {

	private String name;
	private LambdaTerm inner;
	private int numParameters;
	private Visitor<Boolean>[] checks;
	
	/**
	 * Creates a new partial application that has not yet bound any parameters.
	 * @param name The name of the library function.
	 * @param inner The {@link LambdaTerm} for the non-accelerated library function
	 * @param numParameters The number of parameters that the library function takes
	 * @param checks For each parameter, a {@link Visitor} that checks whether the given parameter
	 * has the correct format for acceleration
	 */
	public PartialApplication(String name, LambdaTerm inner, int numParameters, Visitor<Boolean>[] checks) {
		this.name = name;
		this.inner = inner;
		this.numParameters = numParameters;
		this.checks = checks;
	}
	
	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitPartialApplication(this);
	}
	
	/**
	 * Returns the {@link LambdaTerm} that this partial application represents.
	 * @return The {@link LambdaTerm} that this partial application represents
	 */
	public LambdaTerm getRepresented() {
		return null;
	}
	
	/**
	 * Returns the name of the library function for the partial application.
	 * @return The name of the library function for the partial application
	 */
	public String getName() {
		return name;
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
	 * @return A {@link LambdaTerm} for the partial application with the new parameter as described above
	 */
	public LambdaTerm accept(LambdaTerm nextParam) {
		return null;
	}
	
	/**
	 * Directly determine the result of the computation given all parameters.
	 * @param parameters The parameters for the computation
	 * @return The result of the computation
	 */
	protected abstract LambdaTerm accelerate(LambdaTerm[] parameters);
	
	@Override
	public abstract boolean equals(Object other);
	
}
