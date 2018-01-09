package edu.kit.wavelength.client.view.action;

/**
 * This action will try to exit the exercise mode and alerts the user when
 * contents of the editor would be overwritten. Otherwise it changes the view
 * from exercise view to standard input view.
 */
public class ExitExerciseMode implements Action {

	/**
	 * Opens a popup window if the editor contains code that would be overwritten.
	 * If this is not the case this method changes the view to standard input view.
	 */
	@Override
	public void run() {
	}

}
