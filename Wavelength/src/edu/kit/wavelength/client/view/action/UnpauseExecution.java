package edu.kit.wavelength.client.view.action;


import edu.kit.wavelength.client.view.App;

/**
 * This action continues the paused reduction process.
 */
public class UnpauseExecution implements Action {

	private static App app = App.get();

	/**
	 * Continues the paused reduction process, disables the step-by-step buttons and
	 * the option menu.
	 */
	@Override
	public void run() {
		// continue execution
		app.executor.unpause();

		// set view components
		app.backwardsButton.setEnabled(false);
		app.forwardButton.setEnabled(false);
		app.reductionOrderBox.setEnabled(false);
		app.exportButtons.forEach(b -> b.setEnabled(false));
		// TODO: lock outputs

		// toggle run/pause button
		app.runButton.setVisible(false);
		app.pauseButton.setVisible(true);
	}
}
