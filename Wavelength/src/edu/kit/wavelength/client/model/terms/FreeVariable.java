package edu.kit.wavelength.client.model.terms;

public class FreeVariable implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	public String getName() {
		return null;
	}

}
