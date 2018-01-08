package edu.kit.wavelength.client.model.reduction;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * The normal reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex is selected for redection.
 *
 */
public final class NormalOrder implements ReductionOrder {
	
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
