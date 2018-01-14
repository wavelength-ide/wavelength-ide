package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This action continues the paused reduction process.
 */
public class UnpauseExecution implements Action {

	private static App app = App.get();

	private static List<Lockable> componentsToLock = Arrays.asList(app.stepBackwardButton(), app.stepForwardButton(),
			app.reductionOrderBox(), app.treeOutput(), app.unicodeOutput());
	static {
		componentsToLock.addAll(app.exerciseButtons());
		componentsToLock.addAll(app.exportFormatButtons());
	}

	/**
	 * Continues the paused reduction process, disables the step-by-step buttons and
	 * the option menus.
	 */
	@Override
	public void run() {
		// continue execution
		app.executor().unpause();

		// set view components
		componentsToLock.forEach(Lockable::lock);

		app.runButton().hide();
		app.pauseButton().show();
	}
}
