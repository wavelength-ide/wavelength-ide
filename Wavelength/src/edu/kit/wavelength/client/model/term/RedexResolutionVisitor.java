package edu.kit.wavelength.client.model.term;

final class RedexResolutionVisitor extends TermTransformer {

	private LambdaTerm rhs;

	public RedexResolutionVisitor(LambdaTerm rhs) {
		this.rhs = rhs;
	}

	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return app.accept(rhs);
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		return abs.acceptVisitor(new SubstitutionVisitor(rhs)).acceptVisitor(new IndexAdjustmentVisitor(-1));
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		throw new UnsupportedOperationException("Operand must be the left side of a redex");
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		throw new UnsupportedOperationException("Operand must be the left side of a redex");

	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		throw new UnsupportedOperationException("Operand must be the left side of a redex");
	}

}
