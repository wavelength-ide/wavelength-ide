package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

public class SetOutputFormat implements Action {

	private static App app = App.get();

	/**
	 * Changes the reduction order to the selected one.
	 */
	@Override
	public void run() {
		if (!app.executor().isPaused()) {
			return;
		}
		app.executor().setOutputFormat();
	}

}
