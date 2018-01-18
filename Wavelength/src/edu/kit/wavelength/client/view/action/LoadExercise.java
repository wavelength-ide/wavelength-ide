package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class LoadExercise implements Action {

	private Exercise exercise;

	private static App app = App.get();

	private static List<Hideable> componentsToShow = Arrays.asList(
			app.exitExerciseModeButton(),
			app.showSolutionButton(), 
			app.taskPanel()
			);

	private static List<Lockable> componentsToUnlock = Arrays.asList(
			app.editor(), 
			app.outputFormatBox(),
			app.outputSizeBox(), 
			app.reductionOrderBox()
			);

	static {
		componentsToUnlock.addAll(app.libraryBoxes());
		componentsToUnlock.addAll(app.exerciseButtons());
		componentsToUnlock.addAll(app.exportFormatButtons());
	}

	private static List<Lockable> componentsToLock = Arrays.asList(
			app.stepBackwardButton(), 
			app.stepByStepModeButton(),
			app.stepForwardButton(), 
			app.terminateButton(), 
			app.treeOutput(), 
			app.unicodeOutput()
			);

	/**
	 * Constructs a new action for changing the UI from standard input view to
	 * exercise view.
	 *
	 * @param exercise
	 *            The selected Exercise that should be displayed to the user
	 */
	public LoadExercise(final Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * Reduces the editors width and displays the task in the enabled task view
	 * window. Also shows buttons for exiting this exercise view and for displaying
	 * the sample solution.
	 */
	@Override
	public void run() {
		// terminate running execution
		app.executor().terminate();
		
		// TODO: clear input and output -> leerer String
		app.editor().write("");
		app.unicodeOutput().write("");
		app.treeOutput().write("");
		
		componentsToShow.forEach(Hideable::show);
		componentsToLock.forEach(Lockable::lock);
		componentsToUnlock.forEach(Lockable::unlock);
		
		// hide solution as default
		app.solutionPanel().hide();
		
		// set task and solution to the dedicated panel
		app.taskPanel().write(exercise.getTask());
		app.solutionPanel().write(exercise.getSolution());
		
		// toggle play/pause button
		app.pauseButton().hide();
		app.runButton().show();
	}

}
