package edu.kit.wavelength.client.model.terms;

public class BoundVariable implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}
	
	public int getDeBruijnIndex() {
		return 0;
	}

}
