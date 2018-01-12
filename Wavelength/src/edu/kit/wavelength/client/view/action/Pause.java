package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This class pauses the currently running execution process and allows the user
 * to now navigate through the reduction process himself.
 */
public class Pause implements Action {

	private static App app = App.get();

	private static List<Lockable> unlockOnPause = Arrays.asList(app.reductionOrderBox(), app.stepBackwardButton(),
			app.stepByStepModeButton(), app.stepForwardButton(), app.treeOutput(), app.unicodeOutput());
	static {
		unlockOnPause.addAll(app.exerciseButtons());
		unlockOnPause.addAll(app.libraryBoxes());
		unlockOnPause.addAll(app.exportFormatButtons());
	}

	/**
	 * Pause the running execution and enable the step-by-step buttons. Also allow
	 * output window interactions and enable the reduction order option.
	 */
	@Override
	public void run() {
		// pause the running execution
		app.executor().pause();

		// unlock the needed view components
		unlockOnPause.forEach(Lockable::unlock);
		// pauseButton -> runButton
		app.runButton().show();
		app.pauseButton().hide();
	}
}
