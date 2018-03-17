package edu.kit.wavelength.client.model.term;

final class GetDepthVisitor implements Visitor<Integer> {

	@Override
	public Integer visitAbstraction(Abstraction abs) {
		return abs.getDepth();
	}

	@Override
	public Integer visitApplication(Application app) {
		return app.getDepth();
	}

	@Override
	public Integer visitBoundVariable(BoundVariable var) {
		return var.getDepth();
	}

	@Override
	public Integer visitFreeVariable(FreeVariable var) {
		return var.getDepth();
	}

	@Override
	public Integer visitNamedTerm(NamedTerm term) {
		return term.getDepth();
	}

	@Override
	public Integer visitPartialApplication(PartialApplication app) {
		return app.getDepth();
	}

}
