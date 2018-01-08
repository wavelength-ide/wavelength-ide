package edu.kit.wavelength.client.view.export;

import edu.kit.wavelength.client.model.ExecutionState;

/**
 * This interface encapsulates the available export formats. It translates the
 * current output into the corresponding format.
 */
public interface Export {

	/**
	 * 
	 * @return The String representation of the current output in the corresponding
	 *         format.
	 */
	public String getRepresentation(ExecutionState state);

	public String getName();
	
}
