package edu.kit.wavelength.client.view.api;

/**
 * A Readable is a View whose content can be read.
 * 
 * By implementing this interface a View component provides a means of reading
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
