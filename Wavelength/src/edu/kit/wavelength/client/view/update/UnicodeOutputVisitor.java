package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.webui.components.UnicodeOutput;

public class UnicodeOutputVisitor extends ResolvedNamesVisitor<UnicodeOutput> {

	@Override
	public UnicodeOutput visitApplication(Application app) {
		return null;
	}

	@Override
	public UnicodeOutput visitNamedTerm(NamedTerm term) {
		return null;
	}

	@Override
	public UnicodeOutput visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public UnicodeOutput visitFreeVariable(FreeVariable var) {
		return null;
	}

	@Override
	protected UnicodeOutput visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected UnicodeOutput visitAbstraction(Abstraction abs, String resolvedName) {
		return null;
	}

}
