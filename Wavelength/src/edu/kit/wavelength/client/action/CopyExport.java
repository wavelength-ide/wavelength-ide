package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.view.Readable;

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

	}

	@Override
	public void run() {
	}
}
