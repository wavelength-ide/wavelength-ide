package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.view.Hideable;

/**
 * This action causes the application to leave a state in which the user is
 * displayed an export format.
 */
public class CloseExport implements Action {

	private Hideable blocker;
	private Hideable exporter;

	/**
	 * Constructs a new CloseExport Action
	 * 
	 * @param blocker
	 *            A View that blocks the whole Interface except the export
	 *            window.
	 * @param exporter
	 *            A View that presents the current export.
	 */
	public CloseExport(Hideable blocker, Hideable exporter) {

	}

	@Override
	public void run() {

	}
}
