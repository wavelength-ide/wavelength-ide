package edu.kit.wavelength.client.model.terms;

public interface LambdaTerm {
	public <T> T acceptVisitor(Visitor<T> v);
}
