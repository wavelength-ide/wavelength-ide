package edu.kit.wavelength.client.view;

/**
 * By implementing the Writable interface a View component provides a means of giving
 *  it an input represented by a String.
 *  
 */
public interface Writable {
	
	/**
	 * Writes the input in the View.
	 * @param text String representation of an input
	 */
	void write(final String input);
}
