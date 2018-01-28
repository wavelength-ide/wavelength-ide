package edu.kit.wavelength.client.model.term.parsing;

/**
 * Token objects are used by both the {@link Tokeniser} and the {@link Parser}
 * during the construction of an {@link LambdaTerm} from an input String. Each token
 * contains a part of the input String which can be accessed through this class'
 * interface.
 *
 */
public class Token {

	public static final int LBRACKET = 0; 
	public static final int RBRACKET = 1;
	public static final int NAME = 2;
	public static final int LAMBDA = 4;
	public static final int DOT = 5;
	public static final int EQUALS = 6;
	public static final int NEWLINE = 7;
	public static final int SPACE = 8;
	
	private final String content;
	
	private int type;
	
	/**
	 * Creates a new Token containing the entered String and type.
	 * @param content The String to be stored in the Token
	 * @param type The Token's type
	 */
	public Token(String content, int type) {
		this.content = content;
		this.type = type;
	}

	/**
	 * Used to access the String that makes up the token.
	 * 
	 * @return The String making up the token
	 */
	public String getContent() {
		return content;
	}
	
	public int getType() {
		return type;
	}
}
