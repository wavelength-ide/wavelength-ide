package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.action.Action;

/**
 * By implementing this interface Views can have their behaviour changed.
 * 
 *
 */
public interface ExchangableBehaviour {
	
	/**
	 * Replace current behaviour with a new one.
	 * @param action the new Action
	 */
	void exchangeBehavoiur(final Action action);
}
