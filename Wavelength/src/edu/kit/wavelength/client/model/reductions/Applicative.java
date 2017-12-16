package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.LambdaTerm;

public class Applicative implements ReductionOrder {
	
	@Override
	public LambdaTerm next(LambdaTerm term)
	{
		return null;
	}
}
