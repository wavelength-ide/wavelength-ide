package edu.kit.wavelength.client.view;

/**
 * Hideables can be either hidden or shown.
 * Being hidden means that it is still there but not visible on the GUI.
 * 
 */
public interface Hideable {
	
	/**
	 * Make this invisible.
	 */
	void hide();
	
	/**
	 * Make this visible.
	 */
	void show();

}
