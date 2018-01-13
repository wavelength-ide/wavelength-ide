package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action requests and displays the next reduction step of the current
 * execution.
 */
public class StepForward implements Action {

	/**
	 * Requests and displays the next reduction step.
	 */
	@Override
	public void run() {
		App.get().executor().stepForward();
	}
}
