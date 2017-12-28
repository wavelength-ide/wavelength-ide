package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.Application;
import edu.kit.wavelength.client.model.terms.LambdaTerm;

/**
 * The applicative reduction order for the untyped lambda calculus.
 *
 * The rightmost innermost redex is selected for reduction.
 */
class ApplicativeOrder implements ReductionOrder {
	
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
