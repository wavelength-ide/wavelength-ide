package edu.kit.wavelength.client.view;

/**
 * By implementing the Writable interface a View component provides a means of writing
 * its content.
 */
public interface Writable {
	
	/**
	 * Writes content into this element.
	 * 
	 * @param text A String representation of designated new content
	 */
	void write(final String input);
}
