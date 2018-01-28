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

	private String newLine = System.getProperty("line.seperator");
	
	/**
	 * Divides a sequence of characters into Tokens.
	 * 
	 * @param input
	 *            The String to divide into tokens
	 * @return An array containing all tokens
	 */
	public Token[] tokenise(String input) throws ParseException {
		TokenType leftBracket = new TokenType("\\(", Token.LBRACKET);
		TokenType rigthBracket = new TokenType("\\)", Token.RBRACKET);
		TokenType name = new TokenType("[a-zA-Z0-9]++", Token.NAME);
		TokenType lambda = new TokenType("[λ\\\\]", Token.LAMBDA);
		TokenType dot = new TokenType("\\.", Token.DOT);
		TokenType space = new TokenType("\\s++", Token.SPACE);
		TokenType[] types = {leftBracket, rigthBracket, name, lambda, dot, space};	// variable after name!
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
		if (remainingInput.length() == 0) {
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).getType() == Token.SPACE) {
					tokens.remove(i);
				}
			}
			return tokens.toArray(new Token[tokens.size()]);
		} else {
			int column = input.length() - remainingInput.length();
			throw new ParseException("Term could not be parsed, found unknow symbol.", -1, column);
			
		}
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
		types[0] = new TokenType("[a-zA-Z]*", Token.NAME);
		types[1] = new TokenType("\\s*=\\s*", Token.EQUALS);
		types[2] = new TokenType("**[^" + newLine + "]", Token.NEWLINE);
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
