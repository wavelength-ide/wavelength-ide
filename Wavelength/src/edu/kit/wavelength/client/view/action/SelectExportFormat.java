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


	@Override
	public void run() {
		if (!app.executor().isPaused()) {
			app.executor().pause();
			Control.updateControls();
		}
		String reprk = exportFormat.getRepresentation(app.executor().getDisplayed(), app.executor().getLibraries());
		app.exportArea().setText(reprk);
		int lineCount = reprk.split("\r\n|\r|\n", -1).length;
		app.exportArea().setVisibleLines(Math.min(30, lineCount));
		app.exportPopup().show();
		app.exportArea().setFocus(true);
		app.exportArea().selectAll();

	}
}
