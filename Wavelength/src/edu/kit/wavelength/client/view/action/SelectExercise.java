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
		this.loadExerciseAction = loadExerciseAction;
		this.selected = selected;
	}
	
	
	@Override
	public void run() {
		loadExerciseAction.setExercise(selected);
		app.setCurrentExercise(selected);
		app.loadExercisePopup().show();
	}
	
	public Exercise getExercise() {
		return this.selected;
	}
}
