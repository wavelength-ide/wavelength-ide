package edu.kit.wavelength.client.view.update;

import java.util.List;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;

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
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class UnicodeTermVisitor extends ResolvedNamesVisitor<HTML> {
	
	public UnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public HTML visitApplication(Application app) {
		HTML left = app.getLeftHandSide().acceptVisitor(this);
		HTML right = app.getRightHandSide().acceptVisitor(this);
		// TODO: instanceof umgehen
		if (app.getLeftHandSide() instanceof Abstraction) {
			// get anchor from left abs

			Anchor a = new Anchor("");
			a.addClickHandler(event -> new StepManually(app).run());
		}	
			
		String value =  "(" + left + ") (" + right  + ")";
		return new HTML(value);
	}

	@Override
	public HTML visitNamedTerm(NamedTerm term) {
		return new HTML(term.getName());
	}

	@Override
	public HTML visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public HTML visitFreeVariable(FreeVariable var) {
		return new HTML(var.getName());
	}

	@Override
	protected HTML visitBoundVariable(BoundVariable var, String resolvedName) {
		return new HTML(resolvedName);
	}

	@Override
	protected HTML visitAbstraction(Abstraction abs, String resolvedName) {
		HTML inner = abs.getInner().acceptVisitor(this);
		Anchor a = new Anchor("U+03BB" + resolvedName);
		return  new HTML(a + "." + inner);
	}

}
