package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action causes the application to transition back in the {@link Input} (or
 * {@link ExerciseInput}) state.
 */
public class Stop implements Action {

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
