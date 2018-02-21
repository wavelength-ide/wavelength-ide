package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

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
	private boolean grey;

	/**
	 * Create a new execution observer for unicode pretty printing.
	 */
	public UpdateUnicodeOutput() {
		wrappedTerms = new ArrayList<>();
		terms = new ArrayList<>();
		grey = false;
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!isSet()) {
			return;
		}

		terms.add(t);

		// determine the selected reduction order and get the redex that will be reduced
		// next
		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		Application nextRedex = currentOrder.next(t);

		// create a new panel to wrap the new unicode term
		FlowPanel wrapper = new FlowPanel("div");
		// toggles the grey background so terms can be distinguished from each other
		wrapper.setStyleName("grey", grey);
		grey = !grey;

		UnicodeTermVisitor visitor = new UnicodeTermVisitor(app.executor().getLibraries(), nextRedex, wrapper);
		UnicodeTuple term = t.acceptVisitor(visitor);

		// remove click-actions from the predecessor
		if (!wrappedTerms.isEmpty()) {
			wrappedTerms.get(wrappedTerms.size() - 1).addStyleName("notclickable");
		}

		// display the new term
		wrapper.add(term.panel);
		wrappedTerms.add(wrapper);
		app.outputArea().add(wrapper);
		// when a new tree was printed, scroll down so the user can see it
		App.autoScroll();
	}

	@Override
	public void removeLastTerm() {
		if (!isSet() || wrappedTerms.isEmpty()) {
			return;
		}

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
		grey = false;
	}

	@Override
	public void reloadLastTerm() {
		if (!isSet()) {
			return;
		}
		FlowPanel toRemove = wrappedTerms.get(wrappedTerms.size() - 1);
		app.outputArea().remove(toRemove);
		wrappedTerms.remove(wrappedTerms.size() - 1);
		LambdaTerm term = terms.get(terms.size() - 1);
		terms.remove(terms.size() - 1);
		this.pushTerm(term);
	}

	private boolean isSet() {
		String format = app.outputFormatBox().getSelectedItemText();
		return format.equals("Unicode Output");
	}

}
