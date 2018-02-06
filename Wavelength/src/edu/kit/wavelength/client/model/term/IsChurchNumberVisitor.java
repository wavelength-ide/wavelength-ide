package edu.kit.wavelength.client.model.term;

public class IsChurchNumberVisitor extends NameAgnosticVisitor<Boolean> {
	
	int abstractionsSeen = 0;

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return false;
	}

	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		++abstractionsSeen;
		return abs.getInner().acceptVisitor(this);
	}

	@Override
	public Boolean visitApplication(Application app) {
		if (abstractionsSeen != 2)
			return false;
		if (!new BoundVariable(2).equals(app.getLeftHandSide()))
			return false;

		return app.getRightHandSide().acceptVisitor(this);
	}

	@Override
	public Boolean visitBoundVariable(BoundVariable var) {
		return new BoundVariable(1).equals(var);
	}

	@Override
	public Boolean visitFreeVariable(FreeVariable var) {
		return false;
	}

}
