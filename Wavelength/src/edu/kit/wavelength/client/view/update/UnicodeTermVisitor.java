package edu.kit.wavelength.client.view.update;

import java.util.List;

import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

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
public class UnicodeTermVisitor extends ResolvedNamesVisitor<Tuple> {
	
	public UnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Tuple visitApplication(Application app) {
		Tuple left = app.getLeftHandSide().acceptVisitor(this);
		Tuple right = app.getRightHandSide().acceptVisitor(this);
		FlowPanel panel = new FlowPanel("span");
		Anchor a = left.a;
		// this is only true if left is an application
		if (a != null) {
			// make applications clickable and highlight it on mouse over
			panel.addStyleName("application");
			a.addStyleName("clickable");
			// when clicked reduce the current application
			a.addClickHandler(event -> new StepManually(app).run());
		}
			
		// TODO: style the application
		panel.add(new Text("("));
		panel.add(left.panel);
		panel.add(new Text(") ("));
		panel.add(right.panel);
		panel.add(new Text(")"));
		return new Tuple(panel, null);
	}

	@Override
	public Tuple visitNamedTerm(NamedTerm term) {
		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(term.getName()));
		return new Tuple(panel, null);
	}

	@Override
	public Tuple visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public Tuple visitFreeVariable(FreeVariable var) {
		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(var.getName()));
		return new Tuple(panel, null);
	}

	@Override
	protected Tuple visitBoundVariable(BoundVariable var, String resolvedName) {
		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(resolvedName));
		return new Tuple(panel, null);
	}

	@Override
	protected Tuple visitAbstraction(Abstraction abs, String resolvedName) {
		Tuple inner = abs.getInner().acceptVisitor(this);
		Anchor a = new Anchor("λ" + resolvedName);
		a.addStyleName("notclickable");
		FlowPanel panel = new FlowPanel("span");
		panel.add(a);
		panel.add(new Text("."));
		panel.add(inner.panel);
		return  new Tuple(panel, a);
	}

}
