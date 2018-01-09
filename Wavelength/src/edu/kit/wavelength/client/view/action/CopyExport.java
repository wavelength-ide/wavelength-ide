package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This action class copies the generated and displayed export output to the
 * users clipboard.
 */
public class CopyExport implements Action {

	/**
	 * Reads the current export output from the export output window and copies it
	 * to the users clipboard.
	 */
	@Override
	public void run() {
		App.get().exportWindow().read();
		// copy to clipboard
	}
}
