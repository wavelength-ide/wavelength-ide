package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.URLSerializer;
import edu.kit.wavelength.client.view.api.Hideable;


/**
 * This action displays the permalink which encodes the current UI state,
 * options, input and output.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the permalink can be set.
 */

public class UseShare implements Action {

	private Hideable shareOutput;
	private URLSerializer serializer;
	
	/**
	 * Constructs a new Share Action.
	 * 
	 * @param panel
	 *            The Panel that presents the Link to the User.
	 */
	public UseShare(Hideable shareOutput, URLSerializer serializer) {
		this.shareOutput = shareOutput;
		this.serializer = serializer;
	}

	@Override
	public void run() {
		if (shareOutput.isShown()) {
			shareOutput.hide();
		} else {
			serializer.serialize();
			shareOutput.show();
		}
	}

}
