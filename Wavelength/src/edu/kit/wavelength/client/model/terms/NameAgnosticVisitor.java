package edu.kit.wavelength.client.model.terms;

public abstract class NameAgnosticVisitor<T> implements Visitor<T> {
	@Override
	public T visitNamedTerm(NamedTerm term)
	{
		return null;
	}
	
	@Override
	public T visitPartialApplication(PartialApplication app)
	{
		return null;
	}

	public abstract T visitAbstraction(Abstraction abs);
	public abstract T visitApplication(Application app);
	public abstract T visitBoundVariable(BoundVariable var);
	public abstract T visitFreeVariable(FreeVariable var);
}
