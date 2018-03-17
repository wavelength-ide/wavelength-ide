package edu.kit.wavelength.client.model.term;

import java.util.List;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;

/**
 * Represents a {@link LambdaTerm} that has a name.
 *
 */
public final class NamedTerm implements LambdaTerm {

	private String name;
	private LambdaTerm inner;
	private int depth;

	public static final char ID = 'n';

	/**
	 * Creates a new named term.
	 * 
	 * @param name
	 *            The name of the term
	 * @param inner
	 *            The actual term that is being named
	 */
	public NamedTerm(String name, LambdaTerm inner) {
		this.name = name;
		this.inner = inner;
		this.depth = inner.acceptVisitor(new GetDepthVisitor()) + 1;
		
		if (this.depth > LambdaTerm.MAX_DEPTH)
			throw new TermTooDeepException();
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitNamedTerm(this);
	}

	/**
	 * Returns the term that the named term represents.
	 * 
	 * @return The term that the named term represents
	 */
	public LambdaTerm getInner() {
		return inner;
	}

	/**
	 * Returns the name of the term.
	 * 
	 * @return The name of the term
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the depth of this named term.
	 * @return The depth of this named term
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof NamedTerm))
			return false;

		NamedTerm term = (NamedTerm) other;

		return term.getName().equals(this.getName()) && term.getInner().equals(this.getInner());
	}

	@Override
	public LambdaTerm clone() {
		return new NamedTerm(name, inner.clone());
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID)
				.append(SerializationUtilities.enclose(new StringBuilder(name), inner.serialize()));
	}

	/**
	 * Restores a serialized named term.
	 * 
	 * @param serialized
	 *            The serialization of a named term
	 * @return The restored named term
	 */
	public static NamedTerm fromSerialized(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == 2;
		return new NamedTerm(extracted.get(0), LambdaTerm.deserialize(extracted.get(1)));
	}
}
