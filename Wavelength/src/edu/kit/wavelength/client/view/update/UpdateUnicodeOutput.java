package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {

	private List<FlowPanel> wrappedTerms;
	private List<LambdaTerm> terms;
	private static App app = App.get();

	public UpdateUnicodeOutput() {
		wrappedTerms = new ArrayList<>();
		terms = new ArrayList<>();
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!app.unicodeIsSet()) {
			return;
		}

		terms.add(t);

		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		Application nextRedex = currentOrder.next(t);

		FlowPanel wrapper = new FlowPanel("div");
		
		// create a new visitor and visit the term
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(app.executor().getLibraries(), nextRedex, wrapper);
		Tuple term = t.acceptVisitor(visitor);

		/*for (FlowPanel panel : wrappedTerms) {
			panel.addStyleName("notclickable");
		}*/
		
		if (!wrappedTerms.isEmpty()) {
			wrappedTerms.get(wrappedTerms.size() - 1).addStyleName("notclickable");
		}

		wrapper.add(term.panel);
		wrappedTerms.add(wrapper);
		// display the new term
		app.outputArea().add(wrapper);
		app.autoScroll();
	}

	public void removeLastTerm() {
		if (!app.unicodeIsSet() || wrappedTerms.isEmpty()) {
			// no term to remove
			return;
		}

		// remove the last term from the list of all displayed terms
		terms.remove(terms.size() - 1);

		FlowPanel toRemove = wrappedTerms.get(wrappedTerms.size() - 1);
		app.outputArea().remove(toRemove);
		wrappedTerms.remove(wrappedTerms.size() - 1);
		reloadLastTerm();
	}

	@Override
	public void clear() {
		wrappedTerms.clear();
		terms.clear();
	}

	@Override
	public void reloadLastTerm() {
		if (!app.unicodeIsSet()) {
			return;
		}

		FlowPanel toRemove = wrappedTerms.get(wrappedTerms.size() - 1);
		app.outputArea().remove(toRemove);
		wrappedTerms.remove(wrappedTerms.size() - 1);
		LambdaTerm term = terms.get(terms.size() - 1);
		terms.remove(terms.size() - 1);
		this.pushTerm(term);		
	}

}
