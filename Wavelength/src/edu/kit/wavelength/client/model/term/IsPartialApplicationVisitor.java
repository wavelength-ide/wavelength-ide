package edu.kit.wavelength.client.model.term;

/**
 * A visitor that returns true iff it was invoked on a partial application.
 *
 */
public final class IsPartialApplicationVisitor extends NameAgnosticVisitor<Boolean> {

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return true;
	}

	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		return false;
	}

	@Override
	public Boolean visitApplication(Application app) {
		return false;
	}

	@Override
	public Boolean visitBoundVariable(BoundVariable var) {
		return false;
	}

	@Override
	public Boolean visitFreeVariable(FreeVariable var) {
		return false;
	}

}
