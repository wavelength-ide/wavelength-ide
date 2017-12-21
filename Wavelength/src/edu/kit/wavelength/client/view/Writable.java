package edu.kit.wavelength.client.view;

/**
 * A Writable is a View whose content can be overwritten.
 * 
 * By implementing this interface a View component provides a means of writing
 * its content.
 */
public interface Writable {

	/**
	 * Writes content into this element.
	 * 
	 * @param text
	 *            A String representation of the designated new content
	 */
	void write(final String input);
}
