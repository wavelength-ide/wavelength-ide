package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.URLSerializer;
import edu.kit.wavelength.client.view.webui.components.TextField;

/**
 * This action toggles the permalink panel. The permalink encodes the current
 * input, output and settings.
 *
 */

public class UseShare implements Action {

	private URLSerializer serializer;

	public UseShare(URLSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Hides the share panel if it is currently shown, otherwise generates the
	 * permalink that encodes the current input, output and settings.
	 */
	@Override
	public void run() {
		TextField sharePanel = App.get().sharePanel();
		if (sharePanel.isShown()) {
			sharePanel.hide();
		} else {
			// TODO: woher kommt der serializer?
			serializer.serialize();
			sharePanel.show();
		}
	}

}
