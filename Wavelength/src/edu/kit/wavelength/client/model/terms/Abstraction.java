package edu.kit.wavelength.client.model.terms;

public class Abstraction implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	public String getPreferredName()
	{
		return null;
	}

}
