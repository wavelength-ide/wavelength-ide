package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.export.Export;

/**
 * This action displays the current output in the selected export format.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the export output can be set.
 */
public class SelectExportFormat<T extends Writable & Hideable> implements Action {

	private T exporter;
	private Hideable blocker;
	private Export exportFormat;
	private ExecutionEngine engine;
	
	/**
	 * Constructs a new SelectExportFormat.
	 * 
	 * @param exporter
	 *            A View that shows the export format to the User.
	 * @param blocker
	 *            A View that blocks the whole Interface except the View that shows
	 *            the export.
	 * @param exportFormat
	 *            The export format the user chose.
	 */
	public SelectExportFormat(T exporter, Hideable blocker, Export exportFormat, ExecutionEngine engine) {
		this.exporter = exporter;
		this.blocker = blocker;
		this.exportFormat = exportFormat;
		this.engine = engine;
	}

	@Override
	public void run() {
		exporter.write(exportFormat.getRepresentation(engine.getDisplayed()));
		blocker.show();
		exporter.show();
	}
}
