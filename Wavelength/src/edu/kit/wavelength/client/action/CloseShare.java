package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.view.Hideable;

/**
 * This Panel closes the share Function, which generates permalink which encodes
 * the current UI state, options, input and output.
 */
public class CloseShare implements Action {

	private Hideable panel;

	/**
	 * Constructs an new CloseShare Action.
	 * 
	 * @param panel
	 *            The panel that should be closed.
	 */
	public CloseShare(Hideable panel) {

	}

	@Override
	public void run() {

	}
}
