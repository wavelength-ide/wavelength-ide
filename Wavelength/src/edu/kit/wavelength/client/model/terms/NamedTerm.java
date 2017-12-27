package edu.kit.wavelength.client.model.terms;

/**
 * Represents a term that has a name.
 *
 */
public class NamedTerm implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}

	/**
	 * Returns the term that the named term represents.
	 * @return The term that the named term represents
	 */
	public LambdaTerm getInner() {
		return null;
	}
	
	/**
	 * Returns the name of the term.
	 * @return The name of the term
	 */
	public String getName() {
		return null;
	}
}
