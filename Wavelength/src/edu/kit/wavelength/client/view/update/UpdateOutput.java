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
import edu.kit.wavelength.client.view.gwt.VisJs;

/**
 * Updates the output.
 *
 */
public class UpdateOutput implements ExecutionObserver {

	// stores all shown panels
	private List<FlowPanel> panels;
	// stores all shown terms and what format was selected at the time of pretty
	// printing
	private List<TermFormatTuple> terms;
	// needed for vis.js network
	private int panelID;
	// states, whether a panel should have grey background to separate unicode terms
	private boolean grey;
	private static App app = App.get();

	/**
	 * Creates a new Output class.
	 */
	public UpdateOutput() {
		this.panels = new ArrayList<>();
		this.terms = new ArrayList<>();
		this.panelID = 0;
		this.grey = false;
	}

	@Override
	public void pushTerm(LambdaTerm term) {
		OutputFormat format = this.determineOutputFormat();
		terms.add(new TermFormatTuple(term, format));

		FlowPanel wrapper = new FlowPanel("div");
		wrapper.getElement().setId("" + panelID);
		panelID += 1;

		Application nextRedex = nextRedex(term);

		switch (format) {
		case UNICODE:
			pushUnicodeTerm(term, wrapper, nextRedex);
			break;
		case TREE:
			pushTreeTerm(term, wrapper, nextRedex);
			break;
		default:
			break;
		}
		// when a new term was printed, scroll down so the user can see it
		App.autoScrollOutput();
	}

	@Override
	public void pushError(String error) {
		terms.add(new TermFormatTuple(null, OutputFormat.UNICODE));
		
		FlowPanel panel = new FlowPanel("div");
		panel.getElement().setId("" + panelID);
		panelID += 1;
		
		panel.setStyleName("grey", grey);
		grey = !grey;
		
		// remove click-actions from the predecessor
		if (!panels.isEmpty()) {
			panels.get(panels.size() - 1).addStyleName("notclickable");
		}

		panel.add(new Text(error));
		panels.add(panel);
		
		app.outputArea().add(panel);
		
		App.autoScrollOutput();
	}

	@Override
	public void removeLastTerm() {
		grey = !grey;
		panelID -= 1;
		terms.remove(terms.size() - 1);
		app.outputArea().remove(panels.get(panels.size() - 1));
		panels.remove(panels.size() - 1);
		// needed to clear the highlighting of the former reduced redex
		reloadLastTerm();
	}

	@Override
	public void clear() {
		panels.clear();
		terms.clear();
		panelID = 0;
		grey = false;
	}

	/**
	 * This method is triggered if the user wants to change the output by selecting
	 * a different format or a different reduction order.
	 */
	@Override
	public void reloadTerm() {
		LambdaTerm term = terms.get(terms.size() - 1).term;
		OutputFormat format = terms.get(terms.size() - 1).format;
		
		if (term == null || format == null) {
			return;
		}
		
		panelID -= 1;
		
		app.outputArea().remove(panels.get(panels.size() - 1));
		panels.remove(panels.size() - 1);
		terms.remove(terms.size() - 1);

		if (format == OutputFormat.UNICODE) {
			grey = !grey;
		}
		this.pushTerm(term);
	}

	/**
	 * This method is triggered if the last displayed term is removed. It causes the
	 * new last displayed term to be reloaded so all highlights are reset.
	 */
	private void reloadLastTerm() {
		LambdaTerm term = terms.get(terms.size() - 1).term;
		OutputFormat format = terms.get(terms.size() - 1).format;
		
		if (term == null || format == null) {
			return;
		}
		
		panelID -= 1;
		
		app.outputArea().remove(panels.get(panels.size() - 1));
		panels.remove(panels.size() - 1);
		
		FlowPanel wrapper = new FlowPanel("div");
		wrapper.getElement().setId("" + panelID);

		Application nextRedex = nextRedex(term);

		switch (format) {
		case UNICODE:
			grey = !grey;
			pushUnicodeTerm(term, wrapper, nextRedex);
			break;
		case TREE:
			pushTreeTerm(term, wrapper, nextRedex);
			break;
		default:
			break;
		}
		// when a new term was printed, scroll down so the user can see it
		App.autoScrollOutput();
		panelID += 1;

	}

	/**
	 * Determines the next redex according to the currently selected reduction
	 * order.
	 */
	private Application nextRedex(LambdaTerm term) {
		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		return currentOrder.next(term);
	}

	/**
	 * Determines the output format according to the currently selected format.
	 */
	private OutputFormat determineOutputFormat() {
		String formatName = app.outputFormatBox().getSelectedItemText();
		switch (formatName) {
		case "Unicode Output":
			return OutputFormat.UNICODE;
		case "Tree Output":
			return OutputFormat.TREE;
		default:
			return null;
		}
	}

	private void pushUnicodeTerm(LambdaTerm t, FlowPanel wrapper, Application nextRedex) {
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(app.executor().getLibraries(), nextRedex, wrapper);
		UnicodeTuple term = t.acceptVisitor(visitor);
		wrapper.setStyleName("grey", grey);
		grey = !grey;

		// remove click-actions from the predecessor
		if (!panels.isEmpty()) {
			panels.get(panels.size() - 1).addStyleName("notclickable");
		}

		// display the new term
		wrapper.add(term.panel);
		panels.add(wrapper);
		app.outputArea().add(wrapper);
	}

	private void pushTreeTerm(LambdaTerm t, FlowPanel wrapper, Application nextRedex) {
		TreeTriple treeTerm = t.acceptVisitor(new TreeTermVisitor(new ArrayList<>(), nextRedex));

		String nodes = "[" + treeTerm.nodes + "]";
		String edges = "[" + treeTerm.edges + "]";
		wrapper.addStyleName("tree");

		panels.add(wrapper);
		// display the new panel and add the tree
		app.outputArea().add(wrapper);
		VisJs.loadNetwork(nodes, edges, wrapper);
	}

}
