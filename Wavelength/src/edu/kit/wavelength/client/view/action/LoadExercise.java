package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.RedexExercise;


/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class LoadExercise implements Action {

	private Exercise exercise;

	private static App app = App.get();
	
	@Override
	public void run() {
		Control.wipe();
		
		if (exercise instanceof RedexExercise) {
			RedexExercise redexEx = (RedexExercise) exercise;
			redexEx.reset();
		}
		
		if (exercise.hasPredefinitions()) {
			app.editor().write(exercise.getPredefinitions());
		}
		
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
