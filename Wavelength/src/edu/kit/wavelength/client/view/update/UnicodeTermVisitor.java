package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.webui.component.UnicodeTerm;

public class UnicodeTermVisitor extends ResolvedNamesVisitor<UnicodeTerm> {

	@Override
	public UnicodeTerm visitApplication(Application app) {
		return null;
	}

	@Override
	public UnicodeTerm visitNamedTerm(NamedTerm term) {
		return null;
	}

	@Override
	public UnicodeTerm visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public UnicodeTerm visitFreeVariable(FreeVariable var) {
		return null;
	}

	@Override
	protected UnicodeTerm visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected UnicodeTerm visitAbstraction(Abstraction abs, String resolvedName) {
		return null;
	}

}
