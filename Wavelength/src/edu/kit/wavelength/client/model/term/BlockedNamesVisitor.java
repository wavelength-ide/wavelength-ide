package edu.kit.wavelength.client.model.term;

import java.util.Set;
import java.util.TreeSet;

final class BlockedNamesVisitor implements Visitor<Set<String>> {

	@Override
	public Set<String> visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public Set<String> visitAbstraction(Abstraction abs) {
		return abs.getInner().acceptVisitor(this);
	}

	@Override
	public Set<String> visitApplication(Application app) {
		TreeSet<String> t = new TreeSet<String>();
		t.addAll(app.getLeftHandSide().acceptVisitor(this));
		t.addAll(app.getRightHandSide().acceptVisitor(this));
		return t;
	}

	@Override
	public Set<String> visitBoundVariable(BoundVariable var) {
		return java.util.Collections.emptySet();
	}

	@Override
	public Set<String> visitFreeVariable(FreeVariable var) {
		return java.util.Collections.singleton(var.getName());
	}

	@Override
	public Set<String> visitNamedTerm(NamedTerm term) {
		TreeSet<String> t = new TreeSet<String>();
		t.add(term.getName());
		t.addAll(term.getInner().acceptVisitor(this));
		return t;
	}
	
}
