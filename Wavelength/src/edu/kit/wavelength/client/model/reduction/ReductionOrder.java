package edu.kit.wavelength.client.model.reduction;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * Represents a reduction order for the untyped lambda calculus.
 *
 */
public interface ReductionOrder extends Serializable {
	/**
	 * Determines the next redex to be evaluated according to the reduction order.
	 * 
	 * @param term The term whose next redex should be found.
	 * @return <code>null</code> if there is no redex in the given term. Otherwise,
	 * the next redex to be evaluated.
	 */
	Application next(LambdaTerm term);
	
	/**
	 * Returns the name of the reduction order, for example for display
	 * when selecting a reduction order in a user interface.
	 * @return The name of the reduction order.
	 */
	String getName();
}
