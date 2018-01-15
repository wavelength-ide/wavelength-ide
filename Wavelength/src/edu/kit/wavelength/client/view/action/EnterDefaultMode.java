package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This class changes the view from exercise mode view to standard input view.
 */
public class EnterDefaultMode implements Action {

	private static App app = App.get();

	private static List<Hideable> componentsToHide = Arrays.asList(
			app.exitExerciseModeButton(),
			app.hideSolutionButton(), 
			app.showSolutionButton(), 
			app.solutionPanel(), 
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
			app.stepForwardButton(),
			app.terminateButton(), 
			app.treeOutput(), 
			app.unicodeOutput()
			);

	/**
	 * Resizes the editor window to full width, hides the solution and task windows
	 * and hides the buttons for exiting and showing the solution.
	 */
	@Override
	public void run() {
		// terminate the running execution
		app.executor().terminate();

		// set the view components
		componentsToHide.forEach(Hideable::hide);
		componentsToUnlock.forEach(Lockable::unlock);
		componentsToLock.forEach(Lockable::lock);
		app.pauseButton().hide();
		app.runButton().show();

		// TODO: clear exercise panels
		// TODO: clear input and output -> leerer String
	}

}
