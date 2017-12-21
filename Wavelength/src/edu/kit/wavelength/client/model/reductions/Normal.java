package edu.kit.wavelength.client.model.reductions;

import edu.kit.wavelength.client.model.terms.LambdaTerm;

public class Normal implements ReductionOrder {
	
	@Override
	public LambdaTerm next(LambdaTerm term)
	{
		return null;
	}
	
	@Override
	public String serialize() {
		return null;
	}
}
