package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action causes the application to leave the current Exercise state.
 */
public class ExitExerciseMode implements Action {

	@Override
	public void run() {
		App.get().exitExercise();
	}

}
