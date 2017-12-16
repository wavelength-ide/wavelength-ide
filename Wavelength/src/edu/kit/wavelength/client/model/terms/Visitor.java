package edu.kit.wavelength.client.model.terms;

public interface Visitor<T> {
	T visitAbstraction(Abstraction abs);
	T visitApplication(Application app);
	T visitBoundVariable(BoundVariable var);
	T visitFreeVariable(FreeVariable var);
	T visitNamedTerm(NamedTerm term);
}
