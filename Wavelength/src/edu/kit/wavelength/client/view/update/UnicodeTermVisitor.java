package edu.kit.wavelength.client.view.update;

import java.util.List;
import java.util.Objects;

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
import edu.kit.wavelength.client.view.action.StepManually;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class UnicodeTermVisitor extends ResolvedNamesVisitor<Tuple> {

	private boolean bracketsForAbs;
	private boolean bracketsForApp;

	public UnicodeTermVisitor(List<Library> libraries) {
		super(libraries);
		bracketsForAbs = false;
		bracketsForApp = false;
	}

	@Override
	public Tuple visitApplication(Application app) {
		Objects.requireNonNull(app);
		
		final boolean brackets = bracketsForApp;
		resetFlags();

		// An abstraction inside a application should get brackets.
		bracketsForAbs = true;
		Tuple left = app.getLeftHandSide().acceptVisitor(this);

		// If the right term of an application is an application it should get
		// brackets because implicit brackets bind from left to right.
		bracketsForApp = true;
		Tuple right = app.getRightHandSide().acceptVisitor(this);

		FlowPanel panel = new FlowPanel("span");
		Anchor a = left.a;
		// this is only true if left is an application
		if (a != null) {
			// make applications clickable and highlight it on mouse over
			panel.addStyleName("application");
			a.addStyleName("clickable");
			a.addMouseOverHandler(event -> panel.addStyleName("hover"));
			a.addMouseOutHandler(event -> panel.removeStyleName("hover"));
			// when clicked reduce the clicked application
			a.addClickHandler(event -> new StepManually(app).run());
		}

		if (brackets) {
			panel.add(new Text("("));
		}
		panel.add(left.panel);
		panel.add(new Text(" "));
		panel.add(right.panel);
		if (brackets) {
			panel.add(new Text(")"));
		}
		return new Tuple(panel, null);
	}

	@Override
	public Tuple visitNamedTerm(NamedTerm term) {	
		Objects.requireNonNull(term);

		resetFlags();
		
		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(term.getName()));
		return new Tuple(panel, null);
	}

	@Override
	public Tuple visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);
		
		resetFlags();
		
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public Tuple visitFreeVariable(FreeVariable var) {
		Objects.requireNonNull(var);
		
		resetFlags();
		
		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(var.getName()));
		return new Tuple(panel, null);
	}

	@Override
	protected Tuple visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);
		Objects.requireNonNull(resolvedName);
		
		resetFlags();

		FlowPanel panel = new FlowPanel("span");
		panel.add(new Text(resolvedName));
		return new Tuple(panel, null);
	}

	@Override
	protected Tuple visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);
		Objects.requireNonNull(resolvedName);

		// Store bracketsFlag for later use and reset flags.
		final boolean brackets = bracketsForAbs;
		resetFlags();

		// An application inside an abstraction should get brackets.
		bracketsForApp = true;
		
		Tuple inner = abs.getInner().acceptVisitor(this);
		
		Anchor a = new Anchor("λ" + resolvedName);
		a.addStyleName("notclickable");
		
		FlowPanel panel = new FlowPanel("span");
		
		if(brackets) {
			panel.add(new Text("("));
		}
		panel.add(a);
		panel.add(new Text("."));
		panel.add(inner.panel);
		if(brackets) {
			panel.add(new Text(")"));
		}
		
		return new Tuple(panel, a);
	}

	protected final void resetFlags() {
		bracketsForAbs = false;
		bracketsForApp = false;
	}

}
