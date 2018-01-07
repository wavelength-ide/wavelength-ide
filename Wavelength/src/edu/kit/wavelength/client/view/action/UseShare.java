package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.update.Serializer;


/**
 * This action displays the permalink which encodes the current UI state,
 * options, input and output.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the permalink can be set.
 */

public class UseShare<T extends Hideable & Writable> implements Action {

	private T shareOutput;
	private Serializer serializer;
	
	/**
	 * Constructs a new Share Action.
	 * 
	 * @param panel
	 *            The Panel that presents the Link to the User.
	 */
	public UseShare(T shareOutput, Serializer serializer) {
		this.shareOutput = shareOutput;
		this.serializer = serializer;
	}

	@Override
	public void run() {
		if (shareOutput.isShown()) {
			shareOutput.hide();
		} else {
			shareOutput.write(serializer.serialize());
			shareOutput.show();
		}
	}

}
