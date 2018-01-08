package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;

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
	public String getRepresentation(List<LambdaTerm> displayedTerms);

	public String getName();
	
}
