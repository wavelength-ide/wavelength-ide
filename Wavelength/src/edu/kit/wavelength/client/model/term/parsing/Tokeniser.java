package edu.kit.wavelength.client.model.term.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Tokeniser class is used during the first step of the parsing process to
 * turn the entered input into a sequence of Tokens.
 *
 */
public class Tokeniser {

	/**
	 * Divides a sequence of characters into Tokens.
	 * 
	 * @param input
	 *            The String to divide into tokens
	 * @return An array containing all tokens
	 */
	public Token[] tokenise(String input) {
		return null;
	}
	
	/**
	 * Divides an input String into individual tokens for each term name declaration and a single token
	 * containing the remaining part of the input.
	 * @param input The input String containing the name declarations to tokenise
	 * @return An array containing the tokenised input String
	 */
	public Token[] tokenizeLibrary(String input) {
		String newLine = System.getProperty("line.seperator");
		TokenType declarationToken = new TokenType("[a-z][a-zA-Z]\\s*=\\s**\\s*" + newLine, 0);	// declaration		
		ArrayList<Token> tokenList = new ArrayList<Token>();
		String remainingInput = input;
		boolean foundMatch = false;
		do {
			foundMatch = false;
			Matcher typeMatcher = declarationToken.getPattern().matcher(remainingInput);
			if (typeMatcher.find()) {
				foundMatch = true;
				String newContent = typeMatcher.group().trim();
				tokenList.add(new Token(newContent));
				remainingInput = typeMatcher.replaceFirst("");
			}
		} while (foundMatch);
		
		tokenList.add(new Token(remainingInput));
		return tokenList.toArray(new Token[tokenList.size()]);
	}
	
	
	private class TokenType {
		private final Pattern pattern;
		private final int id;
		
		public TokenType(String regex, int id) {
			this.pattern = Pattern.compile("^(" + regex + ")");
			this.id = id;
		}
		
		public Pattern getPattern() {
			return pattern;
		}
		
	}
}
