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

	public TokenType getType() {
		return type;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
}
