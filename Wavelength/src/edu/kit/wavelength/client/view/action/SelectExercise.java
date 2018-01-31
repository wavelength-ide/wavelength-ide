package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This action will try to load a new exercise and alerts the user that the
 * content of the Editor would be overwritten.
 */
public class SelectExercise implements Action {
	
	private static App app = App.get();
	
	private LoadExercise loadExerciseAction;
	private Exercise selected;
	
	/**
	 * Constructor.
	 * @param loadExerciseAction - action to run with selected exercise
	 * @param selected - exercise that is selected when this action fires
	 */
	public SelectExercise(LoadExercise loadExerciseAction, Exercise selected) {
		this.selected = selected;
	}
	
	/**
	 * Opens a PopupWindow if the content of the editor would be overwritten.
	 * Allows the user to choose whether he wants to continue.
	 * Otherwise just loads the selected exercise.
	 */
	@Override
	public void run() {
		loadExerciseAction.setExercise(selected);
		if (selected.hasPredefinitions()) {
			app.loadExercisePopup().show();
		} else {
			loadExerciseAction.run();
		}
	}
}
