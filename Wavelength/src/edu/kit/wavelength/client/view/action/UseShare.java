package edu.kit.wavelength.client.view.action;

import org.gwtbootstrap3.client.ui.TextBox;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.URLSerializer;

/**
 * This action toggles the permalink panel. The permalink encodes the current
 * input, output and settings.
 */

public class UseShare implements Action {

	private URLSerializer serializer;

	/**
	 * Constructs a new action handler for the permalink request.
	 * 
	 * @param serializer
	 *            The instance to delegate the serialization process to
	 */
	public UseShare(URLSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Hides the share panel if it is currently shown, otherwise generates the
	 * permalink that encodes the current input, output and settings.
	 */
	@Override
	public void run() {
		TextBox sharePanel = App.get().sharePanel();
		if (sharePanel.isVisible()) {
			sharePanel.setVisible(false);
		} else {
			serializer.serialize();
			sharePanel.setVisible(true);
		}
	}

}
