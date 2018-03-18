package edu.kit.wavelength.client.model.term;

/**
 * Static class providing utilities for validating lambda terms.
 *
 */
final class TermUtilities {
	private TermUtilities() {
	}
	
	/**
	 * The maximal size of any lambda term.
	 */
	private static final int MAX_SIZE = 130000;
	
	/**
	 * The maximal depth of any lambda term.
	 */
	private static final int MAX_DEPTH = 2000;

	/**
	 * Ensures that a lambda term complies with size and depth constraints.
	 * @param term The lambda term to check
	 */
	public static void validateTerm(LambdaTerm term) {
		if (term.acceptVisitor(new GetSizeVisitor()) > MAX_SIZE)
			throw new TermNotAcceptableException("Term too large.");
		
		if (term.acceptVisitor(new GetDepthVisitor()) > MAX_DEPTH)
			throw new TermNotAcceptableException("Term too deep.");
	}
	
}
