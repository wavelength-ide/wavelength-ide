package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.html.Text;

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

	private List<FlowPanel> terms;
	private static App app = App.get();

	public UpdateUnicodeOutput() {
		terms = new ArrayList<>();
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!app.unicodeIsSet()) {
			return;
		}

		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		Application nextRedex = currentOrder.next(t);

		// create a new visitor and visit the term
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(app.executor().getLibraries(), nextRedex, orderName);
		Tuple term = t.acceptVisitor(visitor);

		// clear the output
		app.outputArea().clear();
		
		// disable clicking on all currently displayed terms
		for (int i = 0; i < terms.size(); i++) {
			FlowPanel wrapper = new FlowPanel();
			wrapper.addStyleName("notclickable");
			wrapper.add(terms.get(i));
			app.outputArea().add(wrapper);
		}

		terms.add(term.panel);
		// display the new term
		app.outputArea().add(term.panel);
		app.autoScroll();
	}

	public void removeLastTerm() {
		if (!app.unicodeIsSet() || terms.isEmpty()) {
			// no term to remove
			return;
		}
		// remove the last term from the list of all displayed terms
		terms.remove(terms.size() - 1);
		// TODO: probably not fast enough
		// clear the output and reset all terms
		app.outputArea().clear();

		// make all terms, except the last not clickable and display them
		for (int i = 0; i < terms.size() - 1; i++) {
			FlowPanel wrap = new FlowPanel("div");
			wrap.addStyleName("notclickable");
			wrap.add(terms.get(i));
			app.outputArea().add(wrap);
		}
		// make the last term clickable and display it
		FlowPanel wrap = new FlowPanel("div");
		wrap.addStyleName("clickable");
		wrap.add(terms.get(terms.size() - 1));
		app.outputArea().add(wrap);
	}

	@Override
	public void clear() {
		terms.clear();
	}

}
