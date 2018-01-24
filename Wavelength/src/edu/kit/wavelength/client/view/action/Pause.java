package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
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

	// UI components that can now be interacted with
	private static List<Lockable> componentsToUnlock = new ArrayList<Lockable>(Arrays.asList(
			app.reductionOrderBox(), 
			app.stepForwardButton(), 
			app.treeOutput(), 
			app.unicodeOutput()
			));
	
	static {
		componentsToUnlock.addAll(app.exportFormatButtons());
	}

	/**
	 * Pause the running execution and enable the step-by-step buttons. Also allow
	 * output window interactions and enable the reduction order option.
	 */
	@Override
	public void run() {
		// pause the running execution
		app.executor().pause();
		
		if (!app.executor().getDisplayed().isEmpty()) {
			componentsToUnlock.add(app.stepBackwardButton());
		}

		// unlock the needed view components
		// only unlock the step backward button if stepping back is possible
		if (!app.executor().getDisplayed().isEmpty()) {
			componentsToUnlock.add(app.stepBackwardButton());
		}
		
		componentsToUnlock.forEach(Lockable::unlock);
		
		// toggle run/pause button
		app.runButton().show();
		app.pauseButton().hide();
	}
}
