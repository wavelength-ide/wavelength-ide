package edu.kit.wavelength.client.model.terms;

public abstract class ResolvedNamesVisitor<T> implements Visitor<T> {
	public abstract T visitAbstraction(Abstraction abs);
	public abstract T visitApplication(Application app);
	
	public T visitBoundVariable(BoundVariable var)
	{
		return null;
	}
	
	public T visitFreeVariable(FreeVariable var)
	{
		return null;
	}
	
	public abstract T visitNamedTerm(NamedTerm term);
	
	protected abstract T visitBoundVariable(BoundVariable var, String resolvedName);
	protected abstract T visitFreeVariable(FreeVariable var, String resolvedName);
}
