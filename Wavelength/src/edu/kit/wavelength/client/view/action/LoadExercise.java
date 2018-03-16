package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;


/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class LoadExercise implements Action {

	private Exercise exercise;

	private static App app = App.get();
	
	/**
	 * Reduces the editors width and displays the task in the enabled task view
	 * window. Also shows buttons for exiting this exercise view and for displaying
	 * the sample solution.
	 */
	@Override
	public void run() {
		Control.wipe();
		
		app.editor().write(exercise.getPredefinitions());
		
		app.exerciseDescriptionLabel().setText(exercise.getTask());
		app.solutionArea().setText(exercise.getSolution());
		
		app.editorExercisePanel().setWidgetHidden(app.exercisePanel(), false);
		app.solutionArea().setVisible(false);
		app.loadExercisePopup().hide();
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
}
