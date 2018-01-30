/*package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.TreeOutput;
import edu.kit.wavelength.client.view.webui.component.TreeTerm;

*//**
 * Observer that updates the {@link TreeOutput} with a new term if it is
 * displayed.
 *//*
public class UpdateTreeOutput implements ExecutionObserver {
	
	private App app = App.get();

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!app.treeOutput().isShown()) {
			return;
		}

		// TODO: libraries?
		TreeTerm term = t.acceptVisitor(new TreeTermVisitor(null));
		app.treeOutput().setTerm(term);
	}

}
*/