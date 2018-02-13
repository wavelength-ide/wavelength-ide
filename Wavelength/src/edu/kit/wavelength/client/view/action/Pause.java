package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class pauses the currently running execution process and allows the user
 * to now navigate through the reduction process himself.
 */
public class Pause implements Action {

	private static App app = App.get();
	
	/**
	 * Pause the running execution and enable the step-by-step buttons. Also allow
	 * output window interactions and enable the reduction order option.
	 */
	@Override
	public void run() {
		app.executor().pause();
		
		Control.updateControls();
	}
}
