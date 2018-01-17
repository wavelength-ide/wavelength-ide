package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

// TODO: Klasse fällt evtl. weg 
/**
 * This action class copies the generated and displayed export output to the
 * users clipboard.
 */
public class CopyExport implements Action {

	/**
	 * Reads the current export output from the export output window and copies it
	 * to the users clipboard.
	 */
	@Override
	public void run() {
		StringSelection export = new StringSelection(App.get().exportWindow().read());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(export, export);	
	}
}
