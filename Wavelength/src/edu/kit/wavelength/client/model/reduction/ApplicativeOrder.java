package edu.kit.wavelength.client.model.reduction;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * The applicative reduction order for the untyped lambda calculus.
 *
 * The rightmost innermost redex is selected for reduction.
 */
final class ApplicativeOrder implements ReductionOrder {
	
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
