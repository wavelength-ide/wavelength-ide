package edu.kit.wavelength.client.model.term;

// A visitor that returns true iff the term it was invoked on is a church number
public class IsChurchNumberVisitor extends NameAgnosticVisitor<Boolean> {
	
	// We are looking for exactly two abstractions followed by some number of nested applications
	// where the left sides are De Bruijn index 2 and the right side is either a new application
	// or, at the very bottom, the De Bruijn index 1.
	int abstractionsSeen = 0;
	
	public IsChurchNumberVisitor(int abstractionsSeen) {
		this.abstractionsSeen = abstractionsSeen;
	}
	
	public IsChurchNumberVisitor() {
		
	}

	@Override
	public Boolean visitPartialApplication(PartialApplication app) {
		return false;
	}

	@Override
	public Boolean visitAbstraction(Abstraction abs) {
		return abs.getInner().acceptVisitor(new IsChurchNumberVisitor(abstractionsSeen + 1));
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
