package edu.kit.wavelength.client.model.term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a {@link LambdaTerm} that consists of a library function that may
 * be accelerated, as well as zero or more applications with arguments for said
 * library function.
 *
 */
public abstract class PartialApplication implements LambdaTerm {

	private String name;
	private LambdaTerm inner;
	private int numParameters;
	private List<Visitor<Boolean>> checks;
	private LambdaTerm[] received;
	private int numReceived;

	/**
	 * Creates a new partial application that has not yet bound any parameters.
	 * 
	 * @param name
	 *            The name of the library function.
	 * @param inner
	 *            The {@link LambdaTerm} for the non-accelerated library function
	 * @param numParameters
	 *            The number of parameters that the library function takes
	 * @param checks
	 *            For each parameter, a {@link Visitor} that checks whether the
	 *            given parameter has the correct format for acceleration
	 */
	protected PartialApplication(String name, LambdaTerm inner, int numParameters, List<Visitor<Boolean>> checks) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(inner);
		Objects.requireNonNull(checks);
		
		if (checks.size() != numParameters)
			throw new IllegalArgumentException("Need exactly as many checks as parameters.");
		
		this.name = name;
		this.inner = inner;
		this.numParameters = numParameters;
		this.checks = checks;
		this.received = new LambdaTerm[numParameters];
		this.numReceived = 0;
	}
	
	protected PartialApplication() {
		// This constructor may only be called if absorbClone is used.
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitPartialApplication(this);
	}

	/**
	 * Returns the {@link LambdaTerm} that this partial application represents.
	 * 
	 * @return The {@link LambdaTerm} that this partial application represents
	 */
	public LambdaTerm getRepresented() {
		LambdaTerm current = inner.clone();

		for (int i = 0; i < numReceived; ++i) {
			current = new Application(current, received[i]);
		}
		
		return current;
	}

	/**
	 * Returns the name of the library function for the partial application.
	 * 
	 * @return The name of the library function for the partial application
	 */
	public String getName() {
		return name;
	}

	/**
	 * Accepts a new parameter for the partial application.
	 * 
	 * If the parameter does not match the format that can be accelerated, returns a
	 * new term representing the unaccelerated application.
	 * 
	 * If the parameter matches the format that can be accelerated, returns the
	 * result of the operation represented by the partial application if all
	 * parameters are now present, or a new PartialApplication representing the
	 * partial application including the given parameter.
	 * 
	 * @param nextParam
	 *            The parameter to be accepted
	 * @return A {@link LambdaTerm} for the partial application with the new
	 *         parameter as described above
	 */
	public LambdaTerm accept(LambdaTerm nextParam) {
		PartialApplication cloned = this.clone();
		cloned.received[numReceived] = nextParam;
		++cloned.numReceived;

		if (!nextParam.acceptVisitor(checks.get(cloned.numReceived - 1)))
			return cloned.getRepresented();
		if (cloned.numReceived == numParameters)
			return cloned.accelerate(received);
		return cloned;
	}

	/**
	 * Directly determine the result of the computation given all parameters.
	 * 
	 * @param parameters
	 *            The parameters for the computation
	 * @return The result of the computation
	 */
	protected abstract LambdaTerm accelerate(LambdaTerm[] parameters);

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		
		if (!(other instanceof PartialApplication))
			return false;
		
		PartialApplication otherPA = (PartialApplication)other;
		
		if (numReceived != otherPA.numReceived)
			return false;
		
		for (int i = 0; i < numReceived; ++i) {
			if (!(received[i].equals(otherPA.received[i])))
				return false;
		}
		
		return true;
	}
	
	// This is a workaround to make up for the lack of Object#clone in GWT.
	// Subclasses of PartialApplication have to call this method in their
	// clone method, so that the received parameters are cloned as well.
	// Also, this method needs a different name.
	protected void absorbClone(PartialApplication other) {
		numParameters = other.numParameters;
		received = new LambdaTerm[numParameters];
		name = other.name;
		inner = other.inner.clone();
		checks = new ArrayList<>(other.checks);
		
		numReceived = other.numReceived;
		
		for (int i = 0; i < numReceived; ++i) {
			received[i] = other.received[i].clone();
		}
	}

	@Override
	public abstract PartialApplication clone();

}
