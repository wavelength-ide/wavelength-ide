package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.view.Hideable;

/**
 * This action causes the application to leave the Export state when the user
 * presses the close button in the export window.
 */
public class CloseExport implements Action {

	private Hideable blocker;
	private Hideable exporter;

	public CloseExport(Hideable blocker, Hideable exporter) {

	}

	@Override
	public void run() {

	}
}
