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

/**
 * Observer that updates the {@link TreeOutput} with a new term if it is
 * displayed.
 */
public class UpdateTreeOutput implements ExecutionObserver {

	private static App app = App.get();
	private List<FlowPanel> panels;
	private List<LambdaTerm> terms;
	private int id;
	public int nodeId;

	/**
	 * Create a new execution observer for tree pretty printing.
	 */
	public UpdateTreeOutput() {
		panels = new ArrayList<>();
		terms = new ArrayList<>();
		id = 0;
		nodeId = 0;
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!isSet()) {
			return;
		}

		terms.add(t);
		id += 1;

		// determine the selected reduction order and get the redex that will be reduced
		// next
		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder currentOrder = ReductionOrders.all().stream().filter(o -> o.getName().equals(orderName))
				.findFirst().get();
		Application nextRedex = currentOrder.next(t);

		TreeTriple term = t.acceptVisitor(new TreeTermVisitor(new ArrayList<>(), this, nextRedex));

		String nodes = "[" + term.nodes + "]";
		String edges = "[" + term.edges + "]";

		// create a panel to wrap the new tree
		FlowPanel treePanel = new FlowPanel("div");
		// the id is needed so the tree will be placed in the correct panel
		treePanel.getElement().setId("" + id);
		treePanel.addStyleName("tree");
		panels.add(treePanel);
		// display the new panel and add the tree
		app.outputArea().add(treePanel);
		VisJs.loadNetwork(nodes, edges, treePanel);
		// when a new tree was printed, scroll down so the user can see it
		App.autoScroll();
	}

	@Override
	public void removeLastTerm() {
		if (!isSet()) {
			return;
		}
		
		id -= 1;

		FlowPanel toRemove = panels.get(panels.size() - 1);
		app.outputArea().remove(toRemove);
		terms.remove(terms.size() - 1);
		panels.remove(panels.size() - 1);
	}

	@Override
	public void clear() {
		panels.clear();
		terms.clear();
		id = 0;
		nodeId = 0;
	}

	@Override
	public void reloadLastTerm() {
		if (!isSet()) {
			return;
		}
		
		FlowPanel toRemove = panels.get(panels.size() - 1);
		app.outputArea().remove(toRemove);
		panels.remove(panels.size() - 1);
		
		LambdaTerm term = terms.get(terms.size() - 1);
		terms.remove(terms.size() - 1);
		this.pushTerm(term);
	}
	
	private boolean isSet() {
		String format = app.outputFormatBox().getSelectedItemText();
		return format.equals("Tree Output");
	}

}
