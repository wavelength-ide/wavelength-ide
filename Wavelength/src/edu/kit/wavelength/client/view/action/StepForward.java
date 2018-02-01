package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action requests and displays the next reduction step of the current
 * execution.
 */
public class StepForward implements Action {

	private static App app = App.get();

	/**
	 * Requests and displays the next reduction step.
	 */
	@Override
	public void run() {
		app.executor().stepForward();

		if (!app.executor().canStepForward()) {
			app.forwardButton().setEnabled(false);
			app.reductionOrderBox().setEnabled(false);
		}
	}
}
