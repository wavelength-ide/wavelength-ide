package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class removes the last shown reduction step from the output.
 */
public class StepBackward implements Action {

	private static App app = App.get();

	/**
	 * Removes the last shown step from the output.
	 */
	@Override
	public void run() {
		app.executor().stepBackward();
		// TODO: determine the selected output format and remove the last displayed term
	
		// lock stepping backwards if stepping back is not possible anymore
		if (app.executor.getDisplayed().isEmpty()) {
			app.backwardsButton().setEnabled(false);
		}
	}

}
