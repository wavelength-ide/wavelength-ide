package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.action.Action;

/**
 * By implementing this interface Views can have their behavior changed.
 */
public interface ExchangableBehaviour {
	
	/**
	 * Replace the current behavior with a new one.
	 * 
	 * @param action the new Action that this view can invoke
	 */
	void exchangeBehavoiur(final Action action);
}
