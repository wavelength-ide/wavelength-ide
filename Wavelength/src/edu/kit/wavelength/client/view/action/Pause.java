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
		// pause the running execution
		app.executor.pause();
		
		// only unlock the step backward button if stepping back is possible
		if (!app.executor.getDisplayed().isEmpty()) {
			app.backwardsButton().setEnabled(true);
		}
		
		// unlock the needed view components
		app.reductionOrderBox().setEnabled(true);
		app.forwardButton().setEnabled(true);
		// TODO: unlock outputs
		app.exerciseButtons().forEach(b -> b.setEnabled(true));

			
		// toggle run/pause button
		app.unpauseButton().setVisible(true);
		app.pauseButton().setVisible(false);
	}
}
