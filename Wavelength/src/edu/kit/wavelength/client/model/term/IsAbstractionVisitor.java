package edu.kit.wavelength.client.model.term;

/**
 * A {@link Visitor} that returns a boolean that is true if the given
 * {@link LambdaTerm} represents an {@link Abstraction} (possibly bound to one
 * or more nested names).
 *
 */
public final class IsAbstractionVisitor extends NameAgnosticVisitor<Boolean> {
	
	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		return true;
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

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return false;
	}

}
