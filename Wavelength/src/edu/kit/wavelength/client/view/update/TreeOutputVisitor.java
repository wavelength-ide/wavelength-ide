package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.webui.components.TreeOutput;

public class TreeOutputVisitor extends ResolvedNamesVisitor<TreeOutput> {

	@Override
	public TreeOutput visitApplication(Application app) {
		return null;
	}

	@Override
	public TreeOutput visitNamedTerm(NamedTerm term) {
		return null;
	}

	@Override
	public TreeOutput visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public TreeOutput visitFreeVariable(FreeVariable var) {
		return null;
	}

	@Override
	protected TreeOutput visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected TreeOutput visitAbstraction(Abstraction abs, String resolvedName) {
		return null;
	}

}
