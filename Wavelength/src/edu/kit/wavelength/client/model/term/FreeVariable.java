package edu.kit.wavelength.client.model.term;

/**
 * Represents a free variable in the untyped lambda calculus.
 * 
 * A free variable has a name. Unlike the name of the variable bound during an
 * abstraction, this name is not a preferred name, but fixed, since it is free
 * over the entire lambda term that is being represented. Changing this name
 * would therefore change the lambda term.
 *
 */
public final class FreeVariable implements LambdaTerm {

	private String name;

	public static final char ID = 'f';

	/**
	 * Creates a new free variable term.
	 * 
	 * @param name
	 *            The name of the free variable being referenced
	 */
	public FreeVariable(String name) {
		this.name = name;
	}

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return v.visitFreeVariable(this);
	}

	/**
	 * Returns the name of the free variable.
	 * 
	 * @return The name of the free variable
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the depth of this free variable.
	 * @return The depth of this free variable
	 */
	public int getDepth() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof FreeVariable))
			return false;

		FreeVariable var = (FreeVariable) other;

		return var.getName().equals(this.getName());
	}

	@Override
	public LambdaTerm clone() {
		return new FreeVariable(name);
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(ID + name);
	}

	/**
	 * Restores a free variable from its serialization.
	 * 
	 * @param serialized
	 *            A serialized free variable
	 * @return The free variable referred to by the serialization
	 */
	public static FreeVariable fromSerialized(String serialized) {
		return new FreeVariable(serialized);
	}

}
