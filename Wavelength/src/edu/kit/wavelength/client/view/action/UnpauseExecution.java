package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This action continues the paused reduction process.
 */
public class UnpauseExecution implements Action {

	private static App app = App.get();

	// UI components that can no longer be interacted with
	private static List<Lockable> componentsToLock = new ArrayList<Lockable>(Arrays.asList(
			app.stepBackwardButton(), 
			app.stepForwardButton(),
			app.reductionOrderBox(), 
			app.treeOutput(), 
			app.unicodeOutput()
			));
	
	static {
		componentsToLock.addAll(app.exportFormatButtons());
	}

	/**
	 * Continues the paused reduction process, disables the step-by-step buttons and
	 * the option menu.
	 */
	@Override
	public void run() {
		// continue execution
		app.executor().unpause();

		// set view components
		componentsToLock.forEach(Lockable::lock);

		// toggle run/pause button
		app.runButton().hide();
		app.pauseButton().show();
	}
}
