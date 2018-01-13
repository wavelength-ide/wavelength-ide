package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class removes the last shown reduction step from the output.
 */
public class StepBackward implements Action {

	/**
	 * Removes the last shown step from the output.
	 */
	@Override
	public void run() {
		// TODO: remove step from output? 
		App.get().executor().stepBackward();
	}

}
