package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * Continues a paused execution, starting at the current point of execution.
 */
public class Unpause implements Action {

	private App app = App.get();

	@Override
	public void run() {
		app.executor().unpause();
		Control.updateControls();
	}

}
