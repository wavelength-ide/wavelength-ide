package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.export.Export;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;

/**
 * This action displays the current output in the selected export format.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the export output can be set.
 */
public class SelectExportFormat<T extends Writable & Hideable> implements Action {

	/*
	 * TODO Es wird noch der Visitor aus dem Model gebraucht, welcher den String
	 * generiert
	 */
	private T exporter;
	private Hideable blocker;
	private Export exportFormat;

	/**
	 * Constructs a new SelectExportFormat.
	 * 
	 * @param exporter
	 *            A View that shows the export format to the User.
	 * @param blocker
	 *            A View that blocks the whole Interface except the View that shows
	 *            the export.
	 * @param exportFormat
	 *            The export format the user choose.
	 */
	public SelectExportFormat(T exporter, Hideable blocker, Export exportFormat) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
