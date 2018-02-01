package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.export.Export;

/**
 * This action displays the currently displayed output in the selected export
 * format in a pop up export window.
 */
public class SelectExportFormat implements Action {

	private Export exportFormat;
	private static App app = App.get();

	/**
	 * Constructs a new action handler for the selection of an export format.
	 * 
	 * @param exportFormat
	 *            The export format the user chose
	 */
	public SelectExportFormat(Export exportFormat) {
		this.exportFormat = exportFormat;
	}

	/**
	 * Gets the representation of the current output in the selected export format
	 * and writes it into the export output window. Displays this window and disables
	 * user interaction with all elements, except the export window.
	 */
	@Override
	public void run() {
		
		//app.exportArea().setText(exportFormat.getRepresentation(app.executor.getDisplayed()));
		// TODO: markiere gesamte Ausgabe
		app.exportPopup().show();;
	}
}
