package edu.kit.wavelength.client.model.term;

// A visitor that determines the numeric value of a church number lambda term.
public class GetChurchNumberVisitor extends NameAgnosticVisitor<Integer> {

	// This visitor actually only counts the number of applications in the
	// right hand sides of the term,
	// which is fine because it must only be used on terms which
	// actually are church numbers.

	@Override
	public Integer visitPartialApplication(PartialApplication app) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer visitAbstraction(Abstraction abs) {
		return abs.getInner().acceptVisitor(this);
	}

	@Override
	public Integer visitApplication(Application app) {
		return app.getRightHandSide().acceptVisitor(this) + 1;
	}

	@Override
	public Integer visitBoundVariable(BoundVariable var) {
		return 0;
	}

	@Override
	public Integer visitFreeVariable(FreeVariable var) {
		throw new UnsupportedOperationException();
	}

}
