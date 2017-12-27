package edu.kit.wavelength.client.model.terms;

/**
 * A visitor that gives non-colliding names for bound variables.
 *
 * @param <T> The return value of the visitor
 */
public abstract class ResolvedNamesVisitor<T> implements Visitor<T> {
	@Override
	public T visitAbstraction(Abstraction abs)
	{
		return null;
	}
	
	@Override
	public abstract T visitApplication(Application app);
	
	@Override
	public T visitBoundVariable(BoundVariable var)
	{
		return null;
	}
	
	@Override
	public abstract T visitNamedTerm(NamedTerm term);
	
	@Override
	public abstract T visitPartialApplication(PartialApplication app);
	
	@Override
	public abstract T visitFreeVariable(FreeVariable var);
	
	/**
	 * Visit a bound variable with an additional non-colliding name for
	 * said variable.
	 * 
	 * This method is provided merely for convenience, the given name
	 * will be the same as the one provided for the given abstraction.
	 * 
	 * @param var The bound variable to be visited
	 * @param resolvedName The resolved name for the bound variable
	 * @return
	 */
	protected abstract T visitBoundVariable(BoundVariable var, String resolvedName);
	
	/**
	 * Visit an abstraction with an additional non-colliding name for
	 * its variable.
	 * 
	 * @param abs The abstraction to be visited
	 * @param resolvedName The resolved name for the variable of this
	 * abstraction
	 * @return
	 */
	protected abstract T visitAbstraction(Abstraction abs, String resolvedName);
}
