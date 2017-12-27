package edu.kit.wavelength.client.model.terms;

/**
 * A visitor that returns a boolean that is true iff the given
 * lambda term represents a redex (possibly bound to one
 * or more nested names).
 *
 */
public class IsRedexVisitor extends NameAgnosticVisitor<Boolean> {

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

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return null;
	}
}
