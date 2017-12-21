package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;

//TODO das Share Panel kann momentan nie wieder geschlossen werden
public class Share<T extends Hideable & Writable> implements Action {

	private UIState state;
	private T panel;

	/**
	 * Constructs a new Share Action.
	 * 
	 * @param panel
	 *            The Panel that presents the Link to the User.
	 */
	public Share(UIState state, T panel) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
