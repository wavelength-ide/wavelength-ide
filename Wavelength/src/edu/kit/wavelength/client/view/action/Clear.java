package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * Terminates the running execution, wipes the output area and updates the UI
 * elements according to the new state.
 */
public class Clear implements Action {

	private static App app = App.get();

	@Override
	public void run() {
		app.executor().terminate();
		app.outputArea().clear();

		Control.updateControls();
	}

}
