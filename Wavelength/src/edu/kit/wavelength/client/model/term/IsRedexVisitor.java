package edu.kit.wavelength.client.model.term;

/**
 * A {@link Visitor} that returns a boolean that is true if the given
 * {@link LambdaTerm} represents a redex (possibly bound to one or more nested
 * names).
 *
 */
public final class IsRedexVisitor extends NameAgnosticVisitor<Boolean> {

	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		return false;
	}

	@Override
	public Boolean visitApplication(Application app) {
		return app.getLeftHandSide().acceptVisitor(new IsAbstractionVisitor())
				|| app.getLeftHandSide().acceptVisitor(new IsPartialApplicationVisitor());
	}

	@Override
	public Boolean visitBoundVariable(BoundVariable var) {
		return false;
	}

	@Override
	public Boolean visitFreeVariable(FreeVariable var) {
		return false;
	}

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return false;
	}
}
