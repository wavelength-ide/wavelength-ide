package edu.kit.wavelength.client.view.action;

import org.gwtbootstrap3.client.ui.TextBox;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.URLSerializer;

/**
 * This action toggles the permalink panel. The permalink encodes the current
 * input, output and settings.
 */

public class UseShare implements Action {

	private static App app = App.get();

	/**
	 * Hides the share panel if it is currently shown, otherwise generates the
	 * permalink that encodes the current input, output and settings.
	 */
	@Override
	public void run() {
		if (app.executor().isRunning()) {
			app.executor().pause();
			Control.updateControls();
		}
		TextBox sharePanel = app.sharePanel();
		if (sharePanel.isVisible()) {
			sharePanel.setVisible(false);
		} else {
			app.urlSerializer().serialize();
			sharePanel.setVisible(true);
		}
	}

}
