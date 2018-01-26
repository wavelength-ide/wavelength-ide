package edu.kit.wavelength.client.model.term.parsing;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The Tokeniser class is used during the first step of the parsing process to
 * turn the entered input into a sequence of Tokens.
 *
 */
public class Tokeniser {

	private String newLine = System.getProperty("line.seperator");
	
	/**
	 * Divides a sequence of characters into Tokens.
	 * 
	 * @param input
	 *            The String to divide into tokens
	 * @return An array containing all tokens
	 */
	public Token[] tokenise(String input) throws ParseException {
		TokenType leftBracket = new TokenType("\\s*(\\s*", Token.LBRACKET);
		TokenType rigthBracket = new TokenType("\\s*)\\s*", Token.RBRACKET);
		TokenType name = new TokenType("\\s*[a-zA-Z0-9]*\\s*", Token.NAME);
		TokenType variable = new TokenType("\\s*[a-z]\\s*", Token.VARIABLE);
		TokenType lambda = new TokenType("\\s*[λ\\]\\s*", Token.LAMBDA);
		TokenType dot = new TokenType("\\s*\\.\\s*", Token.DOT);
		TokenType[] types = {leftBracket, rigthBracket, name, variable, lambda, dot};	// variable after name!
		String remainingInput = input;
		ArrayList<Token> tokens = new ArrayList<Token>();
		boolean foundMatch;
		do {
			foundMatch = false;
			for (int i = 0; i < types.length; i++) {
				Matcher currentMatcher = types[i].getPattern().matcher(remainingInput);
				if (currentMatcher.find()) {
					foundMatch = true;
					String newContent = currentMatcher.group().trim();
					tokens.add(new Token(newContent, types[i].getID()));
					remainingInput = currentMatcher.replaceFirst("");
					break;
				}
			}
			
		} while (foundMatch);
		if (remainingInput.length() != 0) {
			int column = input.length() - remainingInput.length();
			throw new ParseException("Term could not be parsed, found unknow symbol.", -1, column);
		} else {
			return tokens.toArray(new Token[tokens.size()]);
		}
	}
	
	/**
	 * Divides an input String into individual tokens for each term name declaration and a single token
	 * containing the remaining part of the input.
	 * @param input The input String containing the name declarations to tokenise
	 * @return An array containing the tokenised input String
	 */
	public Token[] tokeniseLibrary(String input) {
		TokenType declarationToken = new TokenType("[a-zA-Z0-9]*\\s*=\\s**\\s*" + newLine, 0);	// declaration		
		TokenType blankLineToken = new TokenType("\\s*" + newLine, 1);
		ArrayList<Token> tokenList = new ArrayList<Token>();
		String remainingInput = input;
		boolean foundMatch = false;
		do {
			foundMatch = false;
			Matcher typeMatcher = declarationToken.getPattern().matcher(remainingInput);
			if (typeMatcher.find()) {
				foundMatch = true;
				String newContent = typeMatcher.group().trim();
				tokenList.add(new Token(newContent, -1));
				remainingInput = typeMatcher.replaceFirst("");
			} else {
				typeMatcher = blankLineToken.getPattern().matcher(remainingInput);
				if (typeMatcher.find()) {
					foundMatch = true;
					remainingInput = typeMatcher.replaceFirst("");
				}
			}
		} while (foundMatch);
		tokenList.add(new Token(remainingInput, -1));
		return tokenList.toArray(new Token[tokenList.size()]);
	}
	
	/**
	 * Divides an input String containing a name assignment into tokens.
	 * The first token will always include the name and the last token the term.
	 * @param input The String to tokenise
	 * @return An array containing the tokenised input String
	 */
	public Token[] tokeniseNameAssignment(String input) {
		Token[] result = new Token[3];
		TokenType[] types = new TokenType[3];
		types[0] = new TokenType("[a-zA-Z]*", 0);
		types[1] = new TokenType("\\s*=\\s*", 1);
		types[2] = new TokenType("**[^" + newLine + "]", 2);
		for (int i = 0; i < 3; i++) {
			Matcher currentMatcher = types[i].getPattern().matcher(input);
			result[i] = new Token(currentMatcher.group().trim(), types[i].getID());
			input = currentMatcher.replaceFirst("");
		} 
		return result;
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
		
		public int getID() {
			return id;
		}
		
	}
}
