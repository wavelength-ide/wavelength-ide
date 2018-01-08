package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action causes the application to leave the {@link AutoExecution} (or
 *{@link ExerciseAutoExecution}) state. The application is then in the {@link StepByStep} (or
 * {@link ExerciseStepByStep}) state.
 */
public class Pause implements Action {

	@Override
	public void run() {
		App.get().pause();
	}
}
