package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.AppController;

/**
 * This action causes the application to transition back in the {@link Input} (or
 * {@link ExerciseInput}) state.
 */
public class Stop implements Action {

	@Override
	public void run() {
		AppController.get().stop();
	}
}
