package edu.kit.wavelength.client.view.api;

/**
 * Expresses that a view has a state that can be set and unset (e.g. a checkbox)
 */
public interface Toggleable {
	
	/**
	 * Sets the state.
	 */
	void set();
	/**
	 * Unsets the state.
	 */
	void unset();
	/**
	 * Checks whether the state is set.
	 * @return whether the state is set
	 */
	boolean isSet();
	
}
