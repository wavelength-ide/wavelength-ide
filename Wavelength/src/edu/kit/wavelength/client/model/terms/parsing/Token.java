package edu.kit.wavelength.client.model.terms.parsing;

/**
 * Token objects are used by both the Tokeniser and the Parser during the construction of an lambda-term from an input String.
 * Each token contains a part of the input String which can be accessed through the interface provided by this class.
 *
 */
public class Token {
	
	/**
	 * Used to access the String that makes up the token.
	 * @return The String that makes up the token
	 */
	public String getContent() {
		return null;
	}
}
