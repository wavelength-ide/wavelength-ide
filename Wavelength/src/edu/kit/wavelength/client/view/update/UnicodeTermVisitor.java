package edu.kit.wavelength.client.view.update;

import java.util.List;
import java.util.Objects;

import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Span;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;
import edu.kit.wavelength.client.view.action.StepManually;
import webdriver.output.UnicodeOutput;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link UnicodeOutput} view.
 */
public class UnicodeTermVisitor extends ResolvedNamesVisitor<UnicodeTuple> {

	private boolean bracketsForAbs;
	private boolean bracketsForApp;
	private Application nextRedex;
	private FlowPanel parent;

	/**
	 * Creates a new ResolvedNamesVisitor for unicode pretty printing.
	 * 
	 * @param libraries
	 *            The libraries to take into account.
	 * @param nextRedex
	 *            The redex that is reduced next with the current
	 *            {@link ReductionOrder}
	 * @param parent
	 *            The panel this term will be wrapped in.
	 */
	public UnicodeTermVisitor(List<Library> libraries, Application nextRedex, FlowPanel parent) {
		super(libraries);
		this.bracketsForAbs = false;
		this.bracketsForApp = false;
		this.nextRedex = nextRedex;
		this.parent = parent;
	}

	private static FlowPanel textSpan(String text) {
		FlowPanel s = new FlowPanel("span");
		s.add(new Text(text));
		s.addStyleName("outputText");
		return s;
	}
	
	@Override
	public UnicodeTuple visitApplication(Application app) {
		Objects.requireNonNull(app);

		final boolean brackets = bracketsForApp;
		resetFlags();

		// An abstraction inside a application should get brackets.
		bracketsForAbs = true;
		UnicodeTuple left = app.getLeftHandSide().acceptVisitor(this);
		// set this value again in case it was changed by the statement above
		bracketsForAbs = true;
		// If the right term of an application is an application it should get
		// brackets because implicit brackets bind from left to right.
		bracketsForApp = true;
		UnicodeTuple right = app.getRightHandSide().acceptVisitor(this);

		// create a new panel to wrap the application
		FlowPanel panel = new FlowPanel("span");
		panel.addStyleName("applicationWrapper");

		// underlines the nextRedex to indicate the redex that the current reduction
		// order will reduce next
		if (app == this.nextRedex) {
			panel.addStyleName("nextRedex");
		}

		parent.addStyleName("parent");

		// get the anchor of the left side of the application for potential manipulation
		Anchor a = left.anchor;

		// if the given application is a redex: make the redex clickable and add
		// styling for hovering over the redex
		if (app.acceptVisitor(new IsRedexVisitor())) {
			clickableRedex(app, panel, a);
		}

		// add all elements to the wrapper panel
		if (brackets) {
			panel.add(textSpan("("));
		}
		panel.add(left.panel);
		panel.add(textSpan(" "));
		panel.add(right.panel);
		if (brackets) {
			panel.add(textSpan(")"));
		}

		return new UnicodeTuple(panel, a);
	}

	@Override
	public UnicodeTuple visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		resetFlags();

		// create a new panel to wrap the named term
		FlowPanel panel = new FlowPanel("span");
		panel.addStyleName("namedTermWrapper");
		// create a new anchor with the given name and make it not clickable by default
		Anchor a = new Anchor(term.getName());
		a.addStyleName("abstraction");
		panel.add(a);
		parent.addStyleName("parent");

		// if the given named term is a redex: make the redex clickable and add
		// styling for hovering over the redex
		if (term.getInner().acceptVisitor(new IsRedexVisitor())) {
			clickableRedex((Application) term.getInner(), panel, a);
			if (term.getInner() == this.nextRedex) {
				panel.addStyleName("nextRedex");
			}
		}

		return new UnicodeTuple(panel, a);
	}

	@Override
	public UnicodeTuple visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);

		resetFlags();

		// create a new panel to wrap the partial application
		FlowPanel panel = new FlowPanel("span");
		// create a new anchor with the given name and make it not clickable by default
		Anchor a = new Anchor(app.getName());
		a.addStyleName("abstraction");
		panel.add(a);

		// display all arguments even if they were already reduced
		for (int i = 0; i < app.getNumReceived(); i++) {
			panel.addStyleName("abstraction");
			panel.add(textSpan(" "));
			panel.add(app.getReceived()[i].acceptVisitor(this).panel);
		}

		return new UnicodeTuple(panel, a);
	}

	@Override
	public UnicodeTuple visitFreeVariable(FreeVariable var) {
		Objects.requireNonNull(var);

		resetFlags();

		return new UnicodeTuple(textSpan(var.getName()), null);
	}

	@Override
	protected UnicodeTuple visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);
		Objects.requireNonNull(resolvedName);

		resetFlags();

		return new UnicodeTuple(textSpan(resolvedName), null);
	}

	@Override
	protected UnicodeTuple visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);
		Objects.requireNonNull(resolvedName);

		// Store bracketsFlag for later use and reset flags.
		final boolean brackets = bracketsForAbs;
		resetFlags();

		UnicodeTuple inner = abs.getInner().acceptVisitor(this);

		// create a new panel to wrap the abstraction
		FlowPanel panel = new FlowPanel("span");
		panel.addStyleName("abstractionWrapper");

		// create a new anchor for the lambda symbol and the given name, make it not
		// clickable by default
		Anchor a = new Anchor("λ" + resolvedName);
		a.addStyleName("abstraction");

		// add all elements to the wrapper panel
		if (brackets) {
			panel.add(textSpan("("));
		}
		panel.add(a);
		panel.add(textSpan("."));
		panel.add(inner.panel);
		if (brackets) {
			panel.add(textSpan(")"));
		}

		return new UnicodeTuple(panel, a);
	}


	// helper method to reset the flags for brackets in pretty printing
	private final void resetFlags() {
		bracketsForAbs = false;
		bracketsForApp = false;
	}

	// helper method to add click- and mouse over/out handlers to a given redex and
	// its panel and anchor
	private void clickableRedex(Application redex, FlowPanel panel, Anchor a) {
		panel.addStyleName("application");
		a.addStyleName("clickable");

		// on mouse over the left side of the redex highlight the redex and remove the
		// underlining of nextRedex
		a.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				parent.setStyleName("parent", false);
				panel.addStyleName("hover");
			}
		});

		// when clicked reduce the clicked application, underline the chosen redex and
		// remove the underlining of nextRedex
		a.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				parent.removeStyleName("nextRedex");
				parent.removeStyleName("parent");
				parent.addStyleName("customClick");
				panel.addStyleName("reduced");
				new StepManually(redex).run();
			}
		});

		// on mouse out remove the highlighting of the hovered redex and underline
		// nextRedex again
		a.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				panel.removeStyleName("hover");
				parent.setStyleName("parent", true);
			}
		});
	}

}
