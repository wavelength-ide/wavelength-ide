package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.AppController;

/**
 * This action causes the application to transition from {@link Input} (or
 * {@link ExerciseInput}) state to {@link AutoExecution} (or
 * {@link ExerciseAutoExecution}) state. It can only be triggered if the current
 * state is {@link} Input (or {@link ExerciseInput}).
 */
public class RunNewExecution implements Action {
	@Override
	public void run() {
		AppController.get().start();
	}
}
