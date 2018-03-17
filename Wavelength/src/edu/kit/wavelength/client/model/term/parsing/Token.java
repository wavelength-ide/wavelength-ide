package edu.kit.wavelength.client.model.term.parsing;

/**
 * Token objects are used by both the {@link Tokeniser} and the {@link Parser}
 * during the construction of an {@link LambdaTerm} from an input String. Each
 * token contains a part of the input String which can be accessed through this
 * class' interface.
 *
 */
public class Token {

	private final String content;
	private TokenType type;
	private int start;
	private int end;

	/**
	 * Creates a new Token containing the entered String and type.
	 * 
	 * @param content
	 *            The String to be stored in the Token
	 * @param type
	 *            The Token's type
	 * @param start
	 *            The inclusive start column of the token
	 * @param end
	 *            The exclusive end column of the token
	 */
	public Token(String content, TokenType type, int start, int end) {
		this.content = content;
		this.type = type;
		this.start = start;
		this.end = end;
	}

	/**
	 * Used to access the String that makes up the token.
	 * 
	 * @return The String making up the token
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns the type of the token.
	 * 
	 * @return The type of the token
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns the start of the token.
	 * 
	 * @return The inclusive start of the token
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Returns the end of the token.
	 * 
	 * @return The exclusive end of the token
	 */
	public int getEnd() {
		return end;
	}
}
