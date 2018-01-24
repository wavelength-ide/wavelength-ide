package edu.kit.wavelength.client.model.term;

/**
 * A {@link Visitor} that substitutes {@link BoundVariable}s with a given De
 * Bruijn index with a given substituent.
 *
 */
public final class SubstitutionVisitor extends TermTransformer {

	private int depth;
	private final LambdaTerm substituent;

	/**
	 * Creates a new substitution visitor.
	 * 
	 * @param substituent
	 *            The term that should be substituted with
	 */
	public SubstitutionVisitor(LambdaTerm substituent) {
		depth = 0;
		this.substituent = substituent;
	}

	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		++depth;
		LambdaTerm res = abs.getInner().acceptVisitor(this);
		--depth;
		return depth == 0 ? res : new Abstraction(abs.getPreferredName(), res);
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		return new Application(app.getLeftHandSide().acceptVisitor(this), app.getRightHandSide().acceptVisitor(this));
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		if (var.getDeBruijnIndex() != depth)
			return var;
		return substituent.clone().acceptVisitor(new IndexAdjustmentVisitor(depth));
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return var;
	}

}
