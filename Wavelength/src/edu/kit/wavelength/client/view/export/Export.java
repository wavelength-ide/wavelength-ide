package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This interface encapsulates the available export formats. It translates the
 * current output into the corresponding format.
 */
public interface Export {

	/**
	 * This method transforms the given lambda terms into the dedicated format.
	 * 
	 * @param displayedTerms
	 *            the terms that should be translated
	 * @param libraries
	 *            the libraries of the application that are used in the terms
	 * @return the String representation of the given terms
	 */
	public String getRepresentation(List<LambdaTerm> displayedTerms, List<Library> libraries);

	/**
	 * This method returns the name of the export format.
	 * 
	 * @return the name of the export format
	 */
	public String getName();

}
