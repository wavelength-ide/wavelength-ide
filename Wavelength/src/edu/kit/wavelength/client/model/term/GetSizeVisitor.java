package edu.kit.wavelength.client.model.term;

final class GetSizeVisitor implements Visitor<Integer> {

	@Override
	public Integer visitAbstraction(Abstraction abs) {
		return abs.getSize();
	}

	@Override
	public Integer visitApplication(Application app) {
		return app.getSize();
	}

	@Override
	public Integer visitBoundVariable(BoundVariable var) {
		return var.getSize();
	}

	@Override
	public Integer visitFreeVariable(FreeVariable var) {
		return var.getSize();
	}

	@Override
	public Integer visitNamedTerm(NamedTerm term) {
		return term.getSize();
	}

	@Override
	public Integer visitPartialApplication(PartialApplication app) {
		return app.getSize();
	}

}
