/*package edu.kit.wavelength.client.view.update;

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

*//**
 * Visitor for generating the output of a {@link LambdaTerm} for the {@link TreeOutput} view.
 *//*
public class TreeTermVisitor extends ResolvedNamesVisitor<TreeTerm> {

	public TreeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeTerm visitApplication(Application app) {
		TreeTerm left = app.getLeftHandSide().acceptVisitor(this);
		TreeTerm right = app.getRightHandSide().acceptVisitor(this);
		TreeTerm application = new TreeTerm("App", left, right);
		application.setAction(app);
		return application;
	}

	@Override
	public TreeTerm visitNamedTerm(NamedTerm term) {
		String name = term.getName();
		return new TreeTerm(name, null, null);
	}

	@Override
	public TreeTerm visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public TreeTerm visitFreeVariable(FreeVariable var) {
		String name = var.getName();
		return new TreeTerm(name, null, null);
	}

	@Override
	protected TreeTerm visitBoundVariable(BoundVariable var, String resolvedName) {
		return null;
	}

	@Override
	protected TreeTerm visitAbstraction(Abstraction abs, String resolvedName) {
		TreeTerm inner = abs.getInner().acceptVisitor(this);
		return new TreeTerm("U+03BB" + resolvedName, inner, null);		
	}

}
*/