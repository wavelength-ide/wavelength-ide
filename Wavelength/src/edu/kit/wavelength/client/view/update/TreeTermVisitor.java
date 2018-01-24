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
import edu.kit.wavelength.client.view.webui.component.TreeOutput;
import edu.kit.wavelength.client.view.webui.component.TreeTerm;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the {@link TreeOutput} view.
 */
public class TreeTermVisitor extends ResolvedNamesVisitor<TreeTerm> {

	public TreeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeTerm visitApplication(Application app) {
		return null;
	}

	@Override
	public TreeTerm visitNamedTerm(NamedTerm term) {
		return null;
	}

	@Override
	public TreeTerm visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public TreeTerm visitFreeVariable(FreeVariable var) {
		return null;
	}

	@Override
	protected TreeTerm visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected TreeTerm visitAbstraction(Abstraction abs, String resolvedName) {
		return null;
	}

}
