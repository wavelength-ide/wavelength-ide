package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;

/**
 * This action causes the application to leave the Export state when the user
 * presses the close button in the export window.
 */
public class CloseExport implements Action {

	private UIState state;

	@Override
	public void run() {
		// state.exitExport();
	}

}
