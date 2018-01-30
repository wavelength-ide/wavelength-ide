package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action will try to exit the exercise mode and alerts the user when
 * contents of the editor would be overwritten. It asks the user for permission
 * to exit the exercise mode.
 */
public class ExitExerciseMode implements Action {

	/**
	 * Opens a popup window if the editor contains code that would be overwritten.
	 */
	@Override
	public void run() {
		App.get().infoPopup.show();
	}

}
