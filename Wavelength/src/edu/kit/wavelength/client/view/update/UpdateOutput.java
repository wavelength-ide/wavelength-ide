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
import edu.kit.wavelength.client.view.gwt.VisJs;

public class UpdateOutput implements ExecutionObserver {

	private List<FlowPanel> panels;
	private List<TermFormatTuple> terms;
	private int panelID;
	public int nodeId;
	private static App app = App.get();
	private boolean grey;

	public UpdateOutput() {
		this.panels = new ArrayList<>();
		this.terms = new ArrayList<>();
		this.panelID = 0;
		this.nodeId = 0;
		this.grey = false;
	}

	@Override
	public void pushTerm(LambdaTerm term) {
		String formatName = app.outputFormatBox().getSelectedItemText();
		OutputFormat format = this.determineFormat(formatName);

		terms.add(new TermFormatTuple(term, format));
		FlowPanel wrapper = new FlowPanel("div");
		wrapper.getElement().setId("" + panelID);

		Application nextRedex = nextRedex(term);

		switch (format) {
		case UNICODE:
			pushUnicodeTerm(term, wrapper, nextRedex);
			break;
		case TREE:
			pushTreeTerm(term, wrapper, nextRedex);
			break;
		}
		// when a new term was printed, scroll down so the user can see it
		App.autoScrollOutput();
		panelID += 1;
	}

	@Override
	public void removeLastTerm() {
		grey = !grey;
		panelID -= 1;
		terms.remove(terms.size() - 1);
		FlowPanel toRemove = panels.get(panels.size() - 1);
		app.outputArea().remove(toRemove);
		panels.remove(panels.size() - 1);
		reloadLastTerm();
	}

	@Override
	public void clear() {
		panels.clear();
		terms.clear();
		panelID = 0;
		nodeId = 0;
		grey = false;
	}

	@Override
	public void reloadTerm() {
		panelID -= 1;
		FlowPanel toRemove = panels.get(panels.size() - 1);
		app.outputArea().remove(toRemove);
		panels.remove(panels.size() - 1);
		LambdaTerm term = terms.get(terms.size() - 1).term;
		OutputFormat format = terms.get(terms.size() - 1).format;

		terms.remove(terms.size() - 1);

		if (format == OutputFormat.UNICODE) {
			grey = !grey;
		}
		this.pushTerm(term);
	}

	/*********
	 * helper methods
	 *********/

	private void reloadLastTerm() {
		panelID -= 1;
		FlowPanel toRemove = panels.get(panels.size() - 1);
		app.outputArea().remove(toRemove);

		panels.remove(panels.size() - 1);

		LambdaTerm term = terms.get(terms.size() - 1).term;
		OutputFormat format = terms.get(terms.size() - 1).format;

		terms.remove(terms.size() - 1);

		terms.add(new TermFormatTuple(term, format));
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
		}
		// when a new term was printed, scroll down so the user can see it
		App.autoScrollOutput();
		panelID += 1;

	}

	private Application nextRedex(LambdaTerm term) {
		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		return currentOrder.next(term);
	}

	private OutputFormat determineFormat(String formatName) {
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
		TreeTriple treeTerm = t.acceptVisitor(new TreeTermVisitor(new ArrayList<>(), this, nextRedex));

		String nodes = "[" + treeTerm.nodes + "]";
		String edges = "[" + treeTerm.edges + "]";
		wrapper.addStyleName("tree");

		panels.add(wrapper);
		// display the new panel and add the tree
		app.outputArea().add(wrapper);
		VisJs.loadNetwork(nodes, edges, wrapper);
	}

}
