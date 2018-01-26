package edu.kit.wavelength.client.view.api;

import edu.kit.wavelength.client.view.action.Action;

/**
 * This interface is intended to enable buttons and other components to have
 * their associated {@link Action} set after creation.
 */
public interface Clickable {

	/**
	 * Sets the current action and removes the old one.
	 * 
	 * @param action
	 *            action that this component can invoke
	 */
	void setAction(final Action action);
}
