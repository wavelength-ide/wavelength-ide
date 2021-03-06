package edu.kit.wavelength.client.model.term;

/**
 * A {@link Visitor} that performs a transformation of some kind of a
 * {@link LambdaTerm}, automatically removing names if their inner term has been
 * changed by the transformation.
 * 
 */
public abstract class TermTransformer implements Visitor<LambdaTerm> {
	@Override
	public LambdaTerm visitNamedTerm(NamedTerm term) {
		LambdaTerm oldTerm = term.getInner();
		LambdaTerm newTerm = term.getInner().acceptVisitor(this);

		return oldTerm.equals(newTerm) ? term : newTerm;
	}

	@Override
	public abstract LambdaTerm visitPartialApplication(PartialApplication app);

	@Override
	public abstract LambdaTerm visitAbstraction(Abstraction abs);

	@Override
	public abstract LambdaTerm visitApplication(Application app);

	@Override
	public abstract LambdaTerm visitBoundVariable(BoundVariable var);

	@Override
	public abstract LambdaTerm visitFreeVariable(FreeVariable var);
}
