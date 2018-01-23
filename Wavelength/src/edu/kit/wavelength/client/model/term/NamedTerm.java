package edu.kit.wavelength.client.model.term;

/**
 * Represents a {@link LambdaTerm} that has a name.
 *
 */
public final class NamedTerm implements LambdaTerm {

	private String name;
	private LambdaTerm inner;
	
	/**
	 * Creates a new named term.
	 * @param name The name of the term
	 * @param inner The actual term that is being named
	 */
	public NamedTerm(String name, LambdaTerm inner) {
		this.name = name;
		this.inner = inner;
	}
	
	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitNamedTerm(this);
	}

	/**
	 * Returns the term that the named term represents.
	 * @return The term that the named term represents
	 */
	public LambdaTerm getInner() {
		return inner;
	}
	
	/**
	 * Returns the name of the term.
	 * @return The name of the term
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		
		if (!(other instanceof NamedTerm))
			return false;
		
		NamedTerm term = (NamedTerm)other;
		
		return term.getName().equals(this.getName()) &&
			term.getInner().equals(this.getInner());
	}
	
	@Override
	public LambdaTerm clone() {
		NamedTerm cloned;
		try {
			cloned = (NamedTerm)super.clone();
		} catch (CloneNotSupportedException ex) {
			// Guaranteed not to occur by the Java standard
			throw new RuntimeException();
		}
		
		cloned.inner = inner.clone();
		
		return cloned;
	}

	@Override
	public String serialize() {
		return null;
	}
}
