package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class closes the pop up window that displays the generated export
 * output. It re-enables the UI after the window is closed.
 * 
 */
public class CloseExport implements Action {

	/**
	 * Hides the export output window and re-enables the UI by hiding the UI
	 * interaction blocker.
	 */
	@Override
	public void run() {
		App.get().exportWindow().hide();
		App.get().uiBlocker().hide();
	}
}
