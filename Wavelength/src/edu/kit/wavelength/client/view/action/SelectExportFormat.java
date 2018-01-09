package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.export.Export;
import edu.kit.wavelength.client.view.webui.component.PopUpWindow;

/**
 * This action displays the currently displayed output in the selected export
 * format in a pop up export window.
 */
public class SelectExportFormat implements Action {

	private Export exportFormat;

	/**
	 * TODO: besserer javadoc 
	 * Constructs a new SelectExportFormat.
	 * 
	 * @param exportFormat
	 *            The export format the user chose.
	 */
	public SelectExportFormat(Export exportFormat) {
		this.exportFormat = exportFormat;
	}

	/**
	 * Gets the representation of the current output in the selected export format
	 * and writes it on the export output window. Displayes this window and disables
	 * user interaction with all elements, except the export window.
	 */
	@Override
	public void run() {
		PopUpWindow exportWindow = App.get().exportWindow();
		exportWindow.write(exportFormat.getRepresentation(App.get().executor().getDisplayed()));
		App.get().uiBlocker().show();
		exportWindow.show();
	}
}
