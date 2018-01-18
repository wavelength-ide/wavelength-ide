package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This action stops the currently running reduction process and re-enables all
 * input related components.
 */
public class Stop implements Action {

	private static App app = App.get();

	// UI components that can no longer be interacted with
	private static List<Lockable> componentsToLock = Arrays.asList(
			app.stepBackwardButton(), 
			app.stepForwardButton(),
			app.terminateButton(),
			app.treeOutput(),
			app.unicodeOutput()
			);

	// UI components that can now be interacted with
	private static List<Lockable> componentsToUnlock = Arrays.asList(
			app.editor(), 
			app.outputFormatBox(),
			app.outputSizeBox(), 
			app.reductionOrderBox(),
			app.stepByStepModeButton()
			);

	static {
		componentsToUnlock.addAll(app.libraryBoxes());
		componentsToUnlock.addAll(app.exerciseButtons());
		componentsToUnlock.addAll(app.exportFormatButtons());
	}

	/**
	 * Re-enables the editor and all option menus and blocks all buttons except the
	 * run button. Does not clear the output view.
	 */
	@Override
	public void run() {
		// terminate running execution
		app.executor().terminate();

		// set view components
		componentsToLock.forEach(Lockable::lock);
		componentsToUnlock.forEach(Lockable::unlock);

		// toggle run/pause button
		app.pauseButton().hide();
		app.runButton().show();
	}
}
