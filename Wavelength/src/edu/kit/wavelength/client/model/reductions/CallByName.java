package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.Application;
import edu.kit.wavelength.client.model.terms.LambdaTerm;

/**
 * The call by name reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex that is not enclosed by an abstraction is
 * selected for reduction.
 *
 */
class CallByName implements ReductionOrder {
	
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
