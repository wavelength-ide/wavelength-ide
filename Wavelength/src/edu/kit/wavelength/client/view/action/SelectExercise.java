package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action will try to load a new exercise and alerts the user that the
 * content of the Editor would be overwritten.
 */
public class SelectExercise implements Action {

	/**
	 * Opens a PopupWindow if the content of the editor would be overwritten.
	 * Allows the user to choose whether he wants to continue.
	 * Otherwise just loads the selected exercise.
	 */
	@Override
	public void run() {
		App.get().infoPopup.show();
	}
}
