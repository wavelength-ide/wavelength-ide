package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.AppController;

/**
 * This action causes the application to leave the current Exercise state.
 */
public class ExitExerciseMode implements Action {

	@Override
	public void run() {
		AppController.get().exitExercise();
	}

}
