package edu.kit.wavelength.client.model.reduction;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * The call by name reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex that is not enclosed by an abstraction is
 * selected for reduction.
 *
 */
final class CallByName implements ReductionOrder {
	
	@Override
	public Application next(LambdaTerm term)
	{
		return null;
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	public String serialize() {
		return null;
	}
}
