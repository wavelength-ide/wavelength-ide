package edu.kit.wavelength.client.model.terms;

public class IsAbstractionVisitor extends NameAgnosticVisitor<Boolean> {

	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		return null;
	}

	@Override
	public Boolean visitApplication(Application app) {
		return null;
	}

	@Override
	public Boolean visitBoundVariable(BoundVariable var) {
		return null;
	}

	@Override
	public Boolean visitFreeVariable(FreeVariable var) {
		return null;
	}

}