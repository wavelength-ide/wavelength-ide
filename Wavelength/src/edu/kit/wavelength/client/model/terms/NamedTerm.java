package edu.kit.wavelength.client.model.terms;

public class NamedTerm implements LambdaTerm {

	@Override
	public <T> T acceptVisitor(Visitor<T> v) {
		return null;
	}

	public LambdaTerm getInner() {
		return null;
	}
	
	public String getName() {
		return null;
	}
}
