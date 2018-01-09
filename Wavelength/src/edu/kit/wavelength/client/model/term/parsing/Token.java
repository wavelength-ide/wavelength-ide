package edu.kit.wavelength.client.model.term.parsing;

/**
 * Token objects are used by both the Tokeniser and the Parser during the construction of an lambda-term from an input String.
 * Each token contains a part of the input String which can be accessed through this class' interface.
 *
 */
public class Token {
	
	/**
	 * Creates a new Token containing the entered String.
	 * @param content The String to be stored in the Token
	 */
	public Token(String content) {
		
	}
	
	/**
	 * Used to access the String that makes up the token.
	 * @return The String making up the token
	 */
	public String getContent() {
		return null;
	}
}
