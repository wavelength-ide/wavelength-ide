package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * Changes the output format to the selected one. This options only affects the
 * last displayed and all further terms. It has no effect on all other displayed
 * terms.
 */
public class SetOutputFormat implements Action {

	private static App app = App.get();

	@Override
	public void run() {
		if (!app.executor().isPaused()) {
			return;
		}
		app.executor().updatedOutputFormat();
	}

}
