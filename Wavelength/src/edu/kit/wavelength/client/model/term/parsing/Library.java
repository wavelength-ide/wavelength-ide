package edu.kit.wavelength.client.model.term.parsing;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/** 
 * This interface is used to interact with the different libraries provided by the application.
 * Each library contains a set of lambda terms and their assigned names.
 * These names can be used in place of terms to both shorten terms and make them easier to understand.
 *
 */
public interface Library {
	
	/**
	 * Returns the LambdaTerm with the specified name.
	 * @param name The name assigned to the desired term
	 * @return The term with the entered name, null on error
	 */
	public abstract LambdaTerm getTerm(String name);
	
	/**
	 * Determines whether the library contains a term with the specified name.
	 * @param name The name to search the library for.
	 * @return True if the library contains a term with the entered name.
	 */
	public abstract boolean containsName(String name);
}
