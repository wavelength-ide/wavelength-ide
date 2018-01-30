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
import edu.kit.wavelength.client.view.action.StepManually;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class UnicodeTermVisitor extends ResolvedNamesVisitor<Tuple> {
	
	public UnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Tuple visitApplication(Application app) {
		Tuple left = app.getLeftHandSide().acceptVisitor(this);
		Tuple right = app.getRightHandSide().acceptVisitor(this);
		// TODO: instanceof umgehen
		if (app.getLeftHandSide() instanceof Abstraction) {
			// get anchor from left abs
			left.a.addClickHandler(event -> new StepManually(app).run());
		}	
			
		String value =  "(" + left + ") (" + right  + ")";
		return new Tuple(new HTML(value), null);
	}

	@Override
	public Tuple visitNamedTerm(NamedTerm term) {
		return new Tuple(new HTML(term.getName()), null);
	}

	@Override
	public Tuple visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public Tuple visitFreeVariable(FreeVariable var) {
		return new Tuple(new HTML(var.getName()), null);
	}

	@Override
	protected Tuple visitBoundVariable(BoundVariable var, String resolvedName) {
		return new Tuple(new HTML(resolvedName), null);
	}

	@Override
	protected Tuple visitAbstraction(Abstraction abs, String resolvedName) {
		Tuple inner = abs.getInner().acceptVisitor(this);
		Anchor a = new Anchor("U+03BB" + resolvedName);
		return  new Tuple(new HTML(a + "." + inner), a);
	}

}
