package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Readable;

/**
 * This action copies the current export output to the clipboard.
 */
public class CopyExport implements Action {

	private Readable exporter;

	/**
	 * Constructs a new CopyExport Action.
	 * 
	 * @param exporter
	 *            The area to copy the export from
	 */
	public CopyExport(Readable exporter) {
		this.exporter = exporter;
	}

	@Override
	public void run() {
		String exported = exporter.read();
		// copy to clipboard
	}
}
