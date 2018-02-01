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
		app.executor().unpause();

		app.backwardsButton().setEnabled(false);
		app.forwardButton().setEnabled(false);
		app.reductionOrderBox().setEnabled(false);

		app.unpauseButton().setVisible(false);
		app.pauseButton().setVisible(true);
	}
}
