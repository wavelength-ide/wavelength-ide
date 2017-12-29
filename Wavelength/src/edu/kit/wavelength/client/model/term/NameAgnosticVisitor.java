package edu.kit.wavelength.client.model.term;

/**
 * A visitor that will treat a named term precisely like the term that
 * it represents.
 *
 * @param <T> The return value of the visitor
 */
public abstract class NameAgnosticVisitor<T> implements Visitor<T> {
	@Override
	public T visitNamedTerm(NamedTerm term)
	{
		return null;
	}

	@Override
	public abstract T visitPartialApplication(PartialApplication app);
	
	@Override
	public abstract T visitAbstraction(Abstraction abs);
	
	@Override
	public abstract T visitApplication(Application app);
	
	@Override
	public abstract T visitBoundVariable(BoundVariable var);
	
	@Override
	public abstract T visitFreeVariable(FreeVariable var);
}
