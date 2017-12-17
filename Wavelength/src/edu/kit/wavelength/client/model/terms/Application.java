package edu.kit.wavelength.client.model.terms;

public class Application implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	public LambdaTerm getLeftHandSide() {
		return null;
	}

}
