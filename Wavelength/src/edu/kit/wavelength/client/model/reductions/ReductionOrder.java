package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.LambdaTerm;

/**
 * Represents a reduction order for the untyped lambda calculus.
 *
 */
public interface ReductionOrder {
	/**
	 * Determines the next redex to be evaluated according to the reduction order.
	 * 
	 * @param term The term whose next redex should be found.
	 * @return <code>null</code> if there is no redex in the given term. Otherwise,
	 * the next redex to be evaluated.
	 */
	LambdaTerm next(LambdaTerm term);
	
	String serialize();
}
