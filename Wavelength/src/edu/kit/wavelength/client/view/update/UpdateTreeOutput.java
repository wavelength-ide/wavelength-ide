package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.TreeOutput;

/**
 * Observer that updates the {@link TreeOutput} with a new term if it is displayed.
 */
public class UpdateTreeOutput implements ExecutionObserver {

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!App.get().treeOutput().isShown()) {
			return;
		}
	}

}
