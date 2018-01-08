package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.export.Export;
import edu.kit.wavelength.client.view.webui.components.PopUpTextBox;

/**
 * This action displays the current output in the selected export format.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the export output can be set.
 */
public class SelectExportFormat<T extends Writable & Hideable> implements Action {

	private Export exportFormat;

	/**
	 * Constructs a new SelectExportFormat.
	 * @param exportFormat
	 *            The export format the user chose.
	 */
	public SelectExportFormat(Export exportFormat) {
		this.exportFormat = exportFormat;
	}

	@Override
	public void run() {
		PopUpTextBox exportWindow = App.get().exportWindow();
		exportWindow.write(exportFormat.getRepresentation(App.get().engine().getDisplayed()));
		App.get().uiBlocker().show();
		exportWindow.show();
	}
}
