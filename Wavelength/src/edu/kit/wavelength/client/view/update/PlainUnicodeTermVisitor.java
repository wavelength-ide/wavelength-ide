package edu.kit.wavelength.client.view.update;

import java.util.List;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.StepManually;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class PlainUnicodeTermVisitor extends ResolvedNamesVisitor<String> {
	
	public PlainUnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String visitApplication(Application app) {
		String left = app.getLeftHandSide().acceptVisitor(this);
		String right = app.getRightHandSide().acceptVisitor(this);
			
		return "(" + left + ") (" + right  + ")";
	}

	@Override
	public String visitNamedTerm(NamedTerm term) {
		return term.getName();
	}

	@Override
	public String visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public String visitFreeVariable(FreeVariable var) {
		return var.getName();
	}

	@Override
	protected String visitBoundVariable(BoundVariable var, String resolvedName) {
		return resolvedName;
	}

	@Override
	protected String visitAbstraction(Abstraction abs, String resolvedName) {
		String inner = abs.getInner().acceptVisitor(this);
		return "λ" + resolvedName + "." + inner;	}

}
