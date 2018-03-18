package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class removes the last shown reduction step from the output and pauses
 * the ongoing execution.
 */
public class StepBackward implements Action {

	private static App app = App.get();

	@Override
	public void run() {
		if (app.executor().isRunning()) {
			app.executor().pause();
			Control.updateControls();
			return;
		}
		if (app.executor().canStepBackward()) {
			app.executor().stepBackward();
			Control.updateControls();
			return;
		}

		app.executor().terminate();
		app.outputArea().clear();
		Control.updateControls();
	}

}
