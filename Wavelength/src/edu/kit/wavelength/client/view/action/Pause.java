package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class pauses the currently running execution process and allows the user
 * to now navigate through the reduction process himself.
 */
public class Pause implements Action {

	private static App app = App.get();
	
	@Override
	public void run() {
		app.executor().pause();
		Control.updateControls();
	}
}
