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
	 * @throws ParseException if the input String can not be tokenised
	 * 			
	 */
	public Token[] tokenise(String input) throws ParseException {
		TokenPattern leftBracket = new TokenPattern("\\(", TokenType.LBRACKET);
		TokenPattern rigthBracket = new TokenPattern("\\)", TokenType.RBRACKET);
		TokenPattern name = new TokenPattern("[a-zA-Z0-9]++", TokenType.NAME);
		TokenPattern lambda = new TokenPattern("[λ\\\\]", TokenType.LAMBDA);
		TokenPattern dot = new TokenPattern("\\.", TokenType.DOT);
		TokenPattern space = new TokenPattern("\\s++", TokenType.SPACE);
		TokenPattern[] types = { leftBracket, rigthBracket, name, lambda, dot, space };
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
					tokens.add(new Token(newContent, types[i].getType()));
					remainingInput = currentMatcher.replaceFirst("");
					break;
				}
			}

		} while (foundMatch);
		if (remainingInput.length() == 0) {
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).getType() == TokenType.SPACE) {
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
	 * Divides an input String containing a name assignment into tokens. The
	 * first token will always include the name and the last token the term.
	 * 
	 * @param input
	 *            The String to tokenise
	 * @return An array containing the tokenised input String
	 */
	public Token[] tokeniseNameAssignment(String input) {
		Token[] result = new Token[3];
		TokenPattern[] types = new TokenPattern[3];
		types[0] = new TokenPattern("[a-zA-Z]*", TokenType.NAME);
		types[1] = new TokenPattern("\\s*=\\s*", TokenType.EQUALS);
		types[2] = new TokenPattern("**[^" + newLine + "]", TokenType.NEWLINE);
		for (int i = 0; i < 3; i++) {
			Matcher currentMatcher = types[i].getPattern().matcher(input);
			result[i] = new Token(currentMatcher.group().trim(), types[i].getType());
			input = currentMatcher.replaceFirst("");
		}
		return result;
	}

	/**
	 * This class is used to ease the process of creating tokens matching
	 * certain regular expressions and assigning them the correct type.
	 *
	 */
	private class TokenPattern {

		private final Pattern pattern;
		private final TokenType type;

		/**
		 * Instances a new TokenPattern object with the entered regex and
		 * {@link TokenType}.
		 * 
		 * @param regex
		 *            The regex to be used
		 * @param type
		 *            The TokenPattern to assign to this TokenPattern
		 */
		public TokenPattern(String regex, TokenType type) {
			this.pattern = Pattern.compile("^(" + regex + ")");
			this.type = type;
		}

		/**
		 * Return a Pattern object compiled from the regex the
		 * {@link TokenPattern} was initialized with.
		 * 
		 * @return The TokenPattern's Pattern
		 */
		public Pattern getPattern() {
			return pattern;
		}

		/**
		 * Returns the {@link TokenType} assigned to this {@link TokenPattern}
		 * 
		 * @return The TokenPattern's type
		 */
		public TokenType getType() {
			return type;
		}
	}
}
