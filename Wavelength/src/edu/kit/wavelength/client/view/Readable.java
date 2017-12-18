package edu.kit.wavelength.client.view;

/**
 * By implementing the Readable interface a View component provides a means of being read 
 *  by returning its content as a String.
 * 
 */
public interface Readable {
	
	/**
	 * Returns the content of a View as a String.
	 * @return String representation of a View´s content.
	 */
	String read();
}
