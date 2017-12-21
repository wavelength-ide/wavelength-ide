package edu.kit.wavelength.client.model.terms;

public abstract class TermTransformer implements Visitor<LambdaTerm> {
	public LambdaTerm visitNamedTerm(NamedTerm term)
	{
		return null;
	}
	
	public LambdaTerm visitPartialApplication(PartialApplication app)
	{
		return null;
	}
	
	public abstract LambdaTerm visitAbstraction(Abstraction abs);
	public abstract LambdaTerm visitApplication(Application app);
	public abstract LambdaTerm visitBoundVariable(BoundVariable var);
	public abstract LambdaTerm visitFreeVariable(FreeVariable var);
}
