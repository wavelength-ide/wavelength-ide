package edu.kit.wavelength.client.model.terms;

public abstract class ResolvedNamesVisitor<T> implements Visitor<T> {
	@Override
	public abstract T visitAbstraction(Abstraction abs);
	
	@Override
	public abstract T visitApplication(Application app);
	
	@Override
	public T visitBoundVariable(BoundVariable var)
	{
		return null;
	}
	
	@Override
	public T visitFreeVariable(FreeVariable var)
	{
		return null;
	}
	
	@Override
	public abstract T visitNamedTerm(NamedTerm term);
	
	@Override
	public abstract T visitPartialApplication(PartialApplication app);
	
	protected abstract T visitBoundVariable(BoundVariable var, String resolvedName);
	protected abstract T visitFreeVariable(FreeVariable var, String resolvedName);
}
