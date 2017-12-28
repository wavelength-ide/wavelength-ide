package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.Application;
import edu.kit.wavelength.client.model.terms.LambdaTerm;

/**
 * The normal reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex is selected for redection.
 *
 */
class NormalOrder implements ReductionOrder {
	
	@Override
	public Application next(LambdaTerm term)
	{
		return null;
	}
	
	@Override
	public String getName() {
		return null;
	}
}
