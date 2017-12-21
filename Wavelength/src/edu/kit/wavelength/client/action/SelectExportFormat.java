package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;

public class SelectExportFormat<T extends Writable & Hideable> implements Action {

	/*
	 * TODO Es wird noch der Visitor aus dem Model gebraucht, welcher den String
	 * generiert
	 */
	private T export;
	private Hideable blocker;

	/**
	 * Constructs a new SelectExportFormat.
	 * 
	 * @param exporter
	 *            A View that shows the export format to the User.
	 * @param blocker
	 *            A View that blocks the whole Interface except the View that
	 *            shows the export.
	 */
	public SelectExportFormat(T exporter, Hideable blocker) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
