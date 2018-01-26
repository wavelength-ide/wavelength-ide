package edu.kit.wavelength.client.model.term.parsing;

import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.library.CustomLibrary;
import edu.kit.wavelength.client.model.library.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class is used to convert an input String into a {@link LambdaTerm}
 * object. If any {@link Libraries} terms are used in the input, the necessary
 * {@link Libraries} have to be passed to the Parser through its constructor.
 *
 */
public class Parser {

	private final List<Library> loadedLibraries;
	private CustomLibrary inputLibrary;
	private Stack<Token> tokens;

	/**
	 * Initializes a new parser.
	 * 
	 * @param libraries
	 *            The {@link Libraries} to be taken into consideration during
	 *            parsing
	 */
	public Parser(List<Library> libraries) {
		this.loadedLibraries = libraries;
		inputLibrary = null;
	}

	/**
	 * Gets a library containing the lambda terms and corresponding names
	 * defined in the the last invocation of {@link #parse(String)}}'s input
	 * String.
	 * 
	 * @return A {@link Library} containing the terms entered by the user with
	 *         their assigned names
	 */
	public Library getInputLibary() {
		return inputLibrary;
	}

	private String getName(String assignment) {
		for (int i = 1; i < assignment.length(); i++) {
			if (assignment.charAt(i) == '=') {
				return assignment.substring(0, i - 1).trim();
			}
		}
		return null;
	}

	private String getTerm(String assignment) {
		for (int i = (assignment.length() - 1); i > 0; i--) {
			if (assignment.charAt(i) == '=') {
				int lastIndex = (assignment.length() - 1);
				return assignment.substring((i + 1), lastIndex).trim();
			}
		}
		return null;
	}

	/**
	 * Parses the input text representation of a lambda term and turns it into a
	 * {@link LambdaTerm} object if successful.
	 * 
	 * @param input
	 *            The String to parse.
	 * @return The parsed {@link LambdaTerm} object
	 * @throws ParseException
	 *             if the input String can not be parsed successfully
	 */
	public LambdaTerm parse(String input) throws ParseException {
		inputLibrary = new CustomLibrary("User Library");
		Tokeniser tokeniser = new Tokeniser();
		Token[] tokenisedInput = tokeniser.tokeniseLibrary(input);
		int start = 0;
		for (int i = 0; i < (tokenisedInput.length - 1); i++) {
			String name = getName(tokenisedInput[i].getContent());
			String termString = getTerm(tokenisedInput[i].getContent());
			try {
				LambdaTerm term = parseTerm(termString);
			} catch (ParseException e) {
				int column = tokenisedInput[i].getContent().length() - termString.length();
				throw new ParseException(e.getMessage(), i, column);
			}
			LambdaTerm term = parseTerm(termString);
			inputLibrary.addTerm(term, name);
		}
		String term = tokenisedInput[tokenisedInput.length - 1].getContent();
		LambdaTerm parsedTerm;
		try {
			parsedTerm = parseTerm(term);
		} catch (ParseException e) {
			int column;
			if (e.getColumn() == -1) {
				column = 0;
			} else {
				column = e.getColumn();
			}
			throw new ParseException(e.getMessage(), (tokenisedInput.length - 1), column);
		}
		return parsedTerm;
	}

	/**
	 * Parses the entered String to a {@link LambdaTerm}.
	 * 
	 * @param input
	 *            The String to parse
	 * @return The parsed LambdaTerm
	 * @throws ParseException
	 *             if the String can not be parsed
	 */
	private LambdaTerm parseTerm(String input) throws ParseException {
		Token[] tokenisedInput = new Tokeniser().tokenise(input);
		tokens = new Stack<Token>();
		for (int i = (tokenisedInput.length - 1); i >= 0; i++) {
			tokens.add(tokenisedInput[i]);
		}

		Token firstToken = tokens.pop();
		ASTNode root = null;
		switch (firstToken.getType()) {
		case Token.DOT:
			throw new ParseException("Not a lambda term.", -1, -1);
		case Token.LAMBDA:
			throw new ParseException("Not a lambda term.", -1, -1);
		case Token.NAME:
			break;
		case Token.VARIABLE:
			root = new ASTVariable().parse();
			break;
		case Token.LBRACKET:
			Token peek = tokens.peek();
			if (peek.getType() == Token.LAMBDA) {	// abs
				root = new ASTAbstraction().parse();
			} else if (peek.getType() == Token.NAME || peek.getType() == Token.VARIABLE) {	// app
				root = new ASTAbstraction().parse();
			} else if (peek.getType() == Token.LBRACKET) {	// app oder abs
				root = new ASTTerm().parse();
			} else {
				throw new ParseException("Not a lambda term.", -1, -1);
			}
			if (tokens.pop().getType() != Token.RBRACKET) {
				throw new ParseException("Mismatched parantheses", -1, -1);
			}
			break;
		case Token.RBRACKET:
			throw new ParseException("Not a lambda term.", -1, -1);
		}
		LambdaTerm actualTerm = root.convert();
		return actualTerm;
	}
	
	private Token peekToken() {
		return tokens.peek();
	}
	
	private Token popToken() {
		return tokens.pop();
	}
	
	/**
	 * Retrieves the term with the specified name from the loaded libraries or the library containing the user's named terms.
	 * @param name The desired term's name
	 * @return The term with the assigned name, null if no library contains a term with this name.
	 */
	private LambdaTerm retrieveTerm(String name) {
		if (inputLibrary != null && inputLibrary.containsName(name)) {
			return inputLibrary.getTerm(name);
		}
		for (Library currentLibrary: loadedLibraries) {
			if (currentLibrary.containsName(name)) {
				return currentLibrary.getTerm(name);
			}
		}
		return null;
	}

	private abstract class ASTNode {

		public abstract ASTNode parse() throws ParseException;
		
		public abstract LambdaTerm convert() throws ParseException;
	}

	private class ASTTerm extends ASTNode {	// abs oder app

		@Override
		public ASTNode parse() throws ParseException {
			// TODO Auto-generated method stub
			Token activeToken = popToken();
			return null;
		}

		@Override
		public LambdaTerm convert() throws ParseException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private class ASTAbstraction extends ASTTerm {
		
		@Override
		public ASTNode parse() throws ParseException {
			//do shit
			return null;
		}
	}
	
	private class ASTApplication extends ASTTerm {
		
		 @Override
		 public ASTNode parse() {
			 //do shit
			 return null;
		 }
	}

	private class ASTName extends ASTNode {	// name

		private String name;
		
		
		@Override
		public ASTNode parse()  {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public LambdaTerm convert() throws ParseException {
			LambdaTerm inner = retrieveTerm(name);
			if (inner != null) {
				return new NamedTerm(name, inner);
			} else {
				//TODO
				throw new ParseException("Unknown name: " + name, -1, -1);
			}
		}

	}

	private class ASTVariable extends ASTNode { // variable

		private String name;
		
		@Override
		public ASTNode parse() throws ParseException {
			Token activeToken = popToken();
			if (activeToken.getType() == Token.VARIABLE) {
				this.name = activeToken.getContent();
			} else {
				throw new ParseException("Unexpected Token", -1, -1);
			}
			return this;
		}

		@Override
		public LambdaTerm convert() {
			return new FreeVariable(name);
		}
	}
}
