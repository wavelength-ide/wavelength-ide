package edu.kit.wavelength.client.model.term.parsing;

import java.util.ArrayList;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * The Tokeniser class is used during the first step of the parsing process to
 * turn the entered input into a sequence of Tokens.
 *
 */
public class Tokeniser {
	
	private String newLine = "\n";

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
				RegExp cRegex = types[i].getRegExp();
				MatchResult mresult = cRegex.exec(remainingInput);
				if (mresult != null && mresult.getGroupCount() > 0) {
					foundMatch = true;
					String newContent = mresult.getGroup(0).trim();
					remainingInput = cRegex.replace(remainingInput, "");
					tokens.add(new Token(newContent, types[i].getType()));
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
			System.out.println(remainingInput.length());
			throw new ParseException("Term could not be parsed, found unknown symbol.", -1, column);

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
			RegExp cRegex = types[i].getRegExp();
			MatchResult mresult = cRegex.exec(input);
			if (mresult.getGroupCount() > 1) {
				mresult.getGroup(1);
				result[i] = new Token(mresult.getGroup(1).trim(), types[i].getType());
				input = cRegex.replace(input, "");
			}
		}
		return result;
	}

	/**
	 * This class is used to ease the process of creating tokens matching
	 * certain regular expressions and assigning them the correct type.
	 *
	 */
	private class TokenPattern {

		private final RegExp pattern;
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
			this.pattern = RegExp.compile("^" + regex);
			//this.pattern = RegExp.compile("^" + regex + ")");
			this.type = type;
		}

		/**
		 * Return a RegExp object compiled from the regex the
		 * {@link TokenPattern} was initialized with.
		 * 
		 * @return The TokenPattern's RegExp
		 */
		public RegExp getRegExp() {
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
