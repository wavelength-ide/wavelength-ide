package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;


/**
 * This action displays the permalink which encodes the current UI state,
 * options, input and output.
 *
 * @param <T>
 *            The referenced T object must be hideable. Also the application
 *            must be able to set its text so the permalink can be set.
 */

public class OpenShare<T extends Hideable & Writable> implements Action {

	private T panel;

	/**
	 * Constructs a new Share Action.
	 * 
	 * @param panel
	 *            The Panel that presents the Link to the User.
	 */
	public OpenShare(T panel) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
