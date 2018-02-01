package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.view.exercise.Exercise;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Button;


/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class LoadExercise implements Action {

	private Exercise exercise;

	private static App app = App.get();

	// UI components to show the user
	private static List<Widget> componentsToShow = new ArrayList<Widget>(Arrays.asList(
			app.closeExerciseButton(),
			app.toggleSolutionButton()));

	// UI components that can now be interacted with
	private static List<ListBox> componentsToUnlock = new ArrayList<ListBox>(Arrays.asList(
			app.outputFormatBox(),
			app.outputSizeBox(), 
			app.reductionOrderBox()));


	// UI components that can no longer be interacted with
	private static List<Button> componentsToLock = new ArrayList<Button>(Arrays.asList(
			app.backwardsButton(), 
			app.forwardButton(), 
			app.cancelButton() ));
	
	/**
	 * Reduces the editors width and displays the task in the enabled task view
	 * window. Also shows buttons for exiting this exercise view and for displaying
	 * the sample solution.
	 */
	@Override
	public void run() {
		// terminate running execution
		//app.executor.terminate();
		
		app.editor.write("");
		// a.unicodeOutput().write("");
		// a.treeOutput().write("");
		
		componentsToShow.forEach(w -> w.setVisible(true));
		app.editorExercisePanel().setWidgetHidden(app.exercisePanel(), false);
		
		app.editor.unlock();
		app.stepByStepButton().setEnabled(true);	
		app.libraryCheckBoxes().forEach(c -> c.setEnabled(true));
		app.exerciseButtons().forEach(b -> b.setEnabled(true));
		app.exportButtons().forEach(b -> b.setEnabled(true));
		componentsToUnlock.forEach(w -> w.setEnabled(true));
		
		componentsToLock.forEach(b -> b.setEnabled(false));
		// TODO: lock outputs
			
		
		// hide solution as default
		app.solutionArea().setVisible(false);
		
		// set task and solution to the dedicated panel
		app.exerciseDescriptionLabel().setText(exercise.getTask());
		app.solutionArea().setText(exercise.getSolution());
		
		// toggle run/pause button
		app.pauseButton().setVisible(false);
		app.unpauseButton().setVisible(false);
		app.runButton().setVisible(true);
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
}
