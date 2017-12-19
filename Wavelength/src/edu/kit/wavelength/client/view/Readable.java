package edu.kit.wavelength.client.view;

/**
 * By implementing the Readable interface a View component provides a means of reading
 * its content.
 */
public interface Readable {
	
	/**
	 * Returns the content of a View as a String.
	 * 
	 * @return String representation of a View's content.
	 */
	String read();
}
