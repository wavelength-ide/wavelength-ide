package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action stops the currently running reduction process and re-enables all
 * input related components.
 */
public class Stop implements Action {

	/**
	 * Re-enables the editor and all option menus and blocks all buttons except the
	 * run button. Does not clear the output view.
	 */
	@Override
	public void run() {
		App app = App.get();
		app.editor().unblock();
		app.reductionOrderBox().unblock();
		app.outputFormatBox().unblock();
		app.outputSizeBox().unblock();
		// TODO: app.runPauseButton().?
		app.terminateButton().block();
		app.stepBackwardsButton().block();
		app.stepByStepModeButton().block();
		app.stepForwardsButton().block();
		// TODO: block current output view
	}
}
