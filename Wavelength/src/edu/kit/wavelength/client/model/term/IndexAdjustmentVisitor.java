package edu.kit.wavelength.client.model.term;

// A visitor that shifts all De Bruijn indices that point outside of the
// term that it was originally invoked on by a given delta
final class IndexAdjustmentVisitor extends TermTransformer {

	// The minimum De Bruijn index that escapes the original term
	private int minimumConsidered;
	private final int delta;

	public IndexAdjustmentVisitor(int delta) {
		minimumConsidered = 1;
		this.delta = delta;
	}

	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return app;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		++minimumConsidered;
		LambdaTerm res = abs.getInner().acceptVisitor(this);
		--minimumConsidered;
		return new Abstraction(abs.getPreferredName(), res);
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		return new Application(app.getLeftHandSide().acceptVisitor(this), app.getRightHandSide().acceptVisitor(this));
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		if (var.getDeBruijnIndex() >= minimumConsidered)
			return new BoundVariable(var.getDeBruijnIndex() + delta);
		return var;
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return var;
	}

}
