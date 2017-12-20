package edu.kit.wavelength.client.view;

/**
 * A Hideable is a View that can be hidden or shown.
 * 
 * Being hidden means that an element is still there but not visible on the UI.
 */
public interface Hideable {
	
	/**
	 * Makes this element invisible.
	 */
	void hide();
	
	/**
	 * Makes this element visible.
	 */
	void show();

}
