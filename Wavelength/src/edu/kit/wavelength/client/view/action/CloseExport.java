package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This action causes the application to leave a state in which the user is
 * displayed an export format.
 * 
 * TODO: exporter generic
 */
public class CloseExport implements Action {

	private Hideable blocker;
	private Hideable exporter;

	/**
	 * Constructs a new CloseExport Action
	 * 
	 * @param blocker
	 *            A View that blocks the whole Interface except the View that
	 *            shows the export.
	 * @param exporter
	 *            A View that presents the current export.
	 */
	public CloseExport(Hideable blocker, Hideable exporter) {
		this.blocker = blocker;
		this.exporter = exporter;
	}

	@Override
	public void run() {
		exporter.hide();
		blocker.hide();
	}
}
