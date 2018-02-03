package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.user.client.ui.FlowPanel;

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
	private List<TreeTuple> terms;
	private int id;
	public int nodeId;
	
	public UpdateTreeOutput() {
		terms = new ArrayList<>();
		id = 0;
		nodeId = 0;
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		id++;
		TreeTuple term = t.acceptVisitor(new TreeTermVisitor(new ArrayList<>(), this));
		terms.add(term);
		
		String nodes = "[" + term.nodes + "]";
		String edges = "[" + term.edges + "]";
		
		FlowPanel treePanel = new FlowPanel("div");
		treePanel.getElement().setId("" + id);
		app.outputArea().add(treePanel);
		//app.outputArea().add(new Text(nodes));
		//app.outputArea().add(new Text(edges));
		
		VisJs tree = new VisJs();
		tree.loadNetwork(nodes, edges, treePanel);
	}

	@Override
	public void removeLastTerm() {
		// TODO Auto-generated method stub
		
	}

}
