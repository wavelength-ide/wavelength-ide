package edu.kit.wavelength.client.export;

/**
 * This interface encapsulates the available export formats.
 */
public interface Export {

	/**
	 * 
	 * @return The String representation of the current output in the corresponding
	 *         format.
	 */
	public String getRepresentation();

}
