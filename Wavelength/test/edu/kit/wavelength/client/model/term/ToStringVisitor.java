package edu.kit.wavelength.client.model.term;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;

final class ToStringVisitor implements Visitor<String> {

	public ToStringVisitor() {
	}

	@Override
	public String visitApplication(Application app) {
		return String.format("(%s)(%s)", app.getLeftHandSide().acceptVisitor(this), app.getRightHandSide().acceptVisitor(this));
	}

	@Override
	public String visitNamedTerm(NamedTerm term) {
		return String.format("%s=(%s)", term.getName(), term.getInner().acceptVisitor(this));
	}

	@Override
	public String visitPartialApplication(PartialApplication app) {
		return "<partial>";
	}

	@Override
	public String visitFreeVariable(FreeVariable var) {
		return String.format("*:%s", var.getName());
	}

	@Override
	public String visitBoundVariable(BoundVariable var) {
		return String.format("%d", var.getDeBruijnIndex());
	}

	@Override
	public String visitAbstraction(Abstraction abs) {
		return String.format("\\%s. (%s)", abs.getPreferredName(), abs.getInner().acceptVisitor(this));
	}

}
