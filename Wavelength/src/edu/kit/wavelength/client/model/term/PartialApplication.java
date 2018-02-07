package edu.kit.wavelength.client.model.term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;

/**
 * Represents a {@link LambdaTerm} that consists of a library function that may
 * be accelerated, as well as zero or more applications with arguments for said
 * library function.
 *
 */
public abstract class PartialApplication implements LambdaTerm {

	private LambdaTerm inner;
	private int numParameters;
	private List<Visitor<Boolean>> checks;
	private LambdaTerm[] received;
	private int numReceived;
	private String name;

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
		LambdaTerm current = new NamedTerm(name, inner.clone());

		for (int i = 0; i < numReceived; ++i) {
			current = new Application(current, received[i]);
		}

		return current;
	}

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

		if (!nextParam.acceptVisitor(cloned.checks.get(cloned.numReceived - 1)))
			return cloned.getRepresented();
		if (cloned.numReceived == numParameters)
			return cloned.accelerate(cloned.received);
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

		PartialApplication otherPA = (PartialApplication) other;

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
		inner = other.inner.clone();
		checks = new ArrayList<>(other.checks);
		name = other.name;

		numReceived = other.numReceived;

		for (int i = 0; i < numReceived; ++i) {
			received[i] = other.received[i].clone();
		}
	}

	@Override
	public abstract PartialApplication clone();

	protected StringBuilder serializeReceived() {
		return SerializationUtilities.serializeList(Arrays.asList(received),
				t -> t == null ? new StringBuilder() : t.serialize());
	}

	protected void deserializeReceived(String serialized) {
		received = SerializationUtilities
				.deserializeList(serialized, s -> s.isEmpty() ? null : LambdaTerm.deserialize(s))
				.toArray(new LambdaTerm[0]);
		for (int i = 0; i < numParameters; ++i) {
			if (received[i] != null)
				++numReceived;
		}
	}

	public static final class Addition extends PartialApplication {

		private static final LambdaTerm inner = new Abstraction("m",
				new Abstraction("n",
						new Abstraction("s",
								new Abstraction("z",
										new Application(new Application(new BoundVariable(4), new BoundVariable(2)),
												new Application(
														new Application(new BoundVariable(3), new BoundVariable(2)),
														new BoundVariable(1)))))));

		public Addition() {
			super("plus", inner, 2, Collections.nCopies(2, new IsChurchNumberVisitor()));
		}

		public static Addition fromSerialized(String serialized) {
			Addition result = new Addition();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("+").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer left = parameters[0].acceptVisitor(new GetChurchNumberVisitor());
			Integer right = parameters[1].acceptVisitor(new GetChurchNumberVisitor());

			return LambdaTerm.churchNumber(left + right);
		}

		@Override
		public PartialApplication clone() {
			Addition cloned = new Addition();
			cloned.absorbClone(this);
			return cloned;
		}

	}

	public static final class Successor extends PartialApplication {

		private static final LambdaTerm inner = new Abstraction("n",
				new Abstraction("s", new Abstraction("z", new Application(new BoundVariable(2), new Application(
						new Application(new BoundVariable(3), new BoundVariable(2)), new BoundVariable(1))))));

		public Successor() {
			super("succ", inner, 1, Collections.nCopies(1, new IsChurchNumberVisitor()));
		}

		public static Successor fromSerialized(String serialized) {
			Successor result = new Successor();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("1").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer val = parameters[0].acceptVisitor(new GetChurchNumberVisitor());

			return LambdaTerm.churchNumber(val + 1);
		}

		@Override
		public PartialApplication clone() {
			Successor cloned = new Successor();
			cloned.absorbClone(this);
			return cloned;
		}
	}

	public static final class Multiplication extends PartialApplication {

		private static final LambdaTerm inner = new Abstraction("m",
				new Abstraction("n",
						new Abstraction("s",
								new Abstraction("z",
										new Application(
												new Application(new BoundVariable(4),
														new Application(new BoundVariable(3), new BoundVariable(2))),
												new BoundVariable(1))))));

		public Multiplication() {
			super("times", inner, 2, Collections.nCopies(2, new IsChurchNumberVisitor()));
		}

		public static Multiplication fromSerialized(String serialized) {
			Multiplication result = new Multiplication();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("*").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer left = parameters[0].acceptVisitor(new GetChurchNumberVisitor());
			Integer right = parameters[1].acceptVisitor(new GetChurchNumberVisitor());

			return LambdaTerm.churchNumber(left * right);
		}

		@Override
		public PartialApplication clone() {
			Multiplication cloned = new Multiplication();
			cloned.absorbClone(this);
			return cloned;
		}

	}

	public static final class Exponentiation extends PartialApplication {

		private static final LambdaTerm inner = new Abstraction("m",
				new Abstraction("n",
						new Abstraction("s",
								new Abstraction("z",
										new Application(new Application(
												new Application(new BoundVariable(3), new BoundVariable(4)),
												new BoundVariable(2)), new BoundVariable(1))))));

		public Exponentiation() {
			super("pow", inner, 2, Collections.nCopies(2, new IsChurchNumberVisitor()));
		}

		public static Exponentiation fromSerialized(String serialized) {
			Exponentiation result = new Exponentiation();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("^").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer left = parameters[0].acceptVisitor(new GetChurchNumberVisitor());
			Integer right = parameters[1].acceptVisitor(new GetChurchNumberVisitor());

			// No need for doubles or binary exponentiation here
			int res = 1;
			for (int i = 0; i < right; ++i) {
				res *= left;
			}

			return LambdaTerm.churchNumber(res);
		}

		@Override
		public PartialApplication clone() {
			Exponentiation cloned = new Exponentiation();
			cloned.absorbClone(this);
			return cloned;
		}

	}

	public static final class Predecessor extends PartialApplication {

		private static final LambdaTerm inner = new Abstraction("n",
				new Abstraction("s",
						new Abstraction("z",
								new Application(
										new Application(
												new Application(new BoundVariable(3),
														new Abstraction("g",
																new Abstraction("h",
																		new Application(new BoundVariable(1),
																				new Application(new BoundVariable(2),
																						new BoundVariable(4)))))),
												new Abstraction("u", new BoundVariable(2))),
										new Abstraction("u", new BoundVariable(1))))));

		public Predecessor() {
			super("pred", inner, 1, Collections.nCopies(1, new IsChurchNumberVisitor()));
		}

		public static Predecessor fromSerialized(String serialized) {
			Predecessor result = new Predecessor();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("0").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer val = parameters[0].acceptVisitor(new GetChurchNumberVisitor());

			return LambdaTerm.churchNumber(Math.max(val - 1, 0));
		}

		@Override
		public PartialApplication clone() {
			Predecessor cloned = new Predecessor();
			cloned.absorbClone(this);
			return cloned;
		}

	}

	public static final class Subtraction extends PartialApplication {
		private static final LambdaTerm inner = new Abstraction("m",
				new Abstraction("n",
						new Application(
								new Application(new BoundVariable(1),
										new Abstraction("n", new Abstraction("s", new Abstraction("z", new Application(
												new Application(
														new Application(new BoundVariable(3),
																new Abstraction("g", new Abstraction("h",
																		new Application(new BoundVariable(1),
																				new Application(new BoundVariable(2),
																						new BoundVariable(4)))))),
														new Abstraction("u", new BoundVariable(2))),
												new Abstraction("u", new BoundVariable(1))))))),
								new BoundVariable(2))));

		public Subtraction() {
			super("minus", inner, 2, Collections.nCopies(2, new IsChurchNumberVisitor()));
		}

		public static Subtraction fromSerialized(String serialized) {
			Subtraction result = new Subtraction();
			result.deserializeReceived(serialized);
			return result;
		}

		@Override
		public StringBuilder serialize() {
			return new StringBuilder("-").append(super.serializeReceived());
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			Integer left = parameters[0].acceptVisitor(new GetChurchNumberVisitor());
			Integer right = parameters[1].acceptVisitor(new GetChurchNumberVisitor());

			return LambdaTerm.churchNumber(Math.max(left - right, 0));
		}

		@Override
		public PartialApplication clone() {
			Subtraction cloned = new Subtraction();
			cloned.absorbClone(this);
			return cloned;
		}

	}
}
