package edu.kit.wavelength.client.view.update;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;
import edu.kit.wavelength.client.view.webui.component.UnicodeTerm;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class UnicodeTermVisitor extends ResolvedNamesVisitor<UnicodeTerm> {

	public UnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UnicodeTerm visitApplication(Application app) {
		UnicodeTerm left = app.getLeftHandSide().acceptVisitor(this);
		UnicodeTerm right = app.getRightHandSide().acceptVisitor(this);
		return new UnicodeTerm("(" + left.getRepresentation() + ") (" + right.getRepresentation() + ")");
	}

	@Override
	public UnicodeTerm visitNamedTerm(NamedTerm term) {
		String name = term.getName();
		return new UnicodeTerm(name);
	}

	@Override
	public UnicodeTerm visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public UnicodeTerm visitFreeVariable(FreeVariable var) {
		String name = var.getName();
		return new UnicodeTerm(name);
	}

	@Override
	protected UnicodeTerm visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected UnicodeTerm visitAbstraction(Abstraction abs, String resolvedName) {
		UnicodeTerm inner = abs.getInner().acceptVisitor(this);
		return new UnicodeTerm("U+03BB" + resolvedName + "." + inner.getRepresentation());
	}

}
