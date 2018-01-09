package edu.kit.wavelength.client.model.library;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/** 
 * This interface is used to interact with the different libraries provided by the application.
 * Each library contains a set of lambda terms and their assigned names.
 * These names can be used in place of terms to both shorten terms and make them easier to understand.
 *
 */
public interface Library extends Serializable {
	
	/**
	 * Returns the LambdaTerm with the specified name.
	 * @param name The name assigned to the desired term
	 * @return The term with the entered name, null on error
	 */
	abstract LambdaTerm getTerm(String name);
	
	/**
	 * Determines whether the library contains a term with the specified name.
	 * @param name The name to search the library for.
	 * @return True if the library contains a term with the entered name.
	 */
	abstract boolean containsName(String name);
	
	/**
	 * Returns the library's name
	 * @return The name of the library
	 */
	abstract String getName();
}
