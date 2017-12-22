package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.action.Action;

/**
 * This interface is intended to enable Buttons to have their associated {@link Action} set after creation.
 */
public interface ActionInjectable {
	
	/**
	 * Sets the current Action.
	 * 
	 * @param action action that this can invoke
	 */
	void setAction(final Action action);
}
