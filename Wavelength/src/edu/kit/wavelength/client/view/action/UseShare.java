package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.URLSerializer;
import edu.kit.wavelength.client.view.webui.components.TextField;


/**
 * This action displays the permalink which encodes the current UI state,
 * options, input and output.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the permalink can be set.
 */

public class UseShare implements Action {

	private URLSerializer serializer;
	
	/**
	 * Constructs a new Share Action.
	 * 

	 */
	public UseShare(URLSerializer serializer) {
		this.serializer = serializer;
	}

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
