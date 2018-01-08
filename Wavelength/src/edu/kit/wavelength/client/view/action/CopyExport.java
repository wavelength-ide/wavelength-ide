package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * This action copies the current export output to the clipboard.
 */
public class CopyExport implements Action {

	@Override
	public void run() {
		App.get().exportWindow().read();
		// copy to clipboard
	}
}
