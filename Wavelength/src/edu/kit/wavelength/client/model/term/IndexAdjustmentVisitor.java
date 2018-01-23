package edu.kit.wavelength.client.model.term;

final class IndexAdjustmentVisitor extends TermTransformer {
	
	private int minimumConsidered;
	private final int delta;
	
	public IndexAdjustmentVisitor(int delta) {
		minimumConsidered = 1;
		this.delta = delta;
	}

	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		// TODO Auto-generated method stub
		return null;
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
		return new Application(app.getLeftHandSide().acceptVisitor(this),
				app.getRightHandSide().acceptVisitor(this));
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
