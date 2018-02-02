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

		boolean canStepForward = app.executor().canStepForward();
		app.forwardButton().setEnabled(canStepForward);
		app.unpauseButton().setEnabled(canStepForward);
		app.reductionOrderBox().setEnabled(canStepForward);
		
		boolean canStepBackward = app.executor().canStepBackward();
		app.backwardsButton().setEnabled(canStepBackward);
	}

}
