package edu.kit.wavelength.client.model.term.parsing;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.library.CustomLibrary;
import edu.kit.wavelength.client.model.library.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		inputLibrary = new CustomLibrary("UserLibrary");
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
		String[] possibleRows = input.split(System.getProperty("line.separator"));
		ArrayList<String> rows = new ArrayList<String>();
		for (int i = 0; i < possibleRows.length; i++) {
			Matcher rowMatcher = Pattern.compile("\\s*").matcher(possibleRows[i]);
			if (rowMatcher.matches() == false) {
				rows.add(possibleRows[i]);
			}
		}
		for (int i = 0; i < (rows.size() - 1); i++) {
			readLibraryTerm(rows.get(i));
		}
		String lastLine = rows.get((rows.size() - 1));
		return parseTerm(lastLine);
	}

	private void readLibraryTerm(String input) throws ParseException {
		Matcher formatMatcher = Pattern.compile("\\s*[a-zA-Z0-9]++\\s*=\\s*.++\\s*").matcher(input);
		if (formatMatcher.matches() == false) {
			throw new ParseException("Not a valid name assignment", -1, -1);
		}
	}

	/**
	 * Returns the topmost token on the stack without removing it.
	 * 
	 * @return The topmost token on the stack
	 */
	private Token peekToken() {
		return tokens.peek();
	}

	/**
	 * Pops a token from the token Stack.
	 * 
	 * @return The topmost token on the stack
	 */
	private Token popToken() {
		return tokens.pop();
	}

	private void pushToken(Token newToken) {
		tokens.push(newToken);
	}
	
	/**
	 * Returns the number of symbols not yet removed from the stack
	 * @return The number of remaining symbols
	 */
	private int numberOfRemainingSymbols() {
		int count = 0;
		while (tokens.isEmpty() == false) {
			count = count + popToken().getContent().length();
		}
		return count;
	}

	/**
	 * Retrieves the term with the specified name from the loaded libraries or
	 * the library containing the user's named terms.
	 * 
	 * @param name
	 *            The desired term's name
	 * @return The term with the assigned name, null if no library contains a
	 *         term with this name.
	 */
	private LambdaTerm retrieveTerm(String name) {
		if (inputLibrary != null && inputLibrary.containsName(name)) {
			return inputLibrary.getTerm(name);
		}
		for (Library currentLibrary : loadedLibraries) {
			if (currentLibrary.containsName(name)) {
				return currentLibrary.getTerm(name);
			}
		}
		return null;
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
		for (int i = (tokenisedInput.length - 1); i >= 0; i--) {
			tokens.add(tokenisedInput[i]);
		}
		ASTNode root = new ASTTerm().parse();
		System.out.println(">>>>>>>>>  " + root.toString());
		if (tokens.isEmpty() == false) {
			throw new ParseException("", -1, -1);
		}
		return root.convert(new ArrayList<String>(), new ArrayList<Integer>());
	}

	/**
	 * The abstract superclass of all classes representing nodes of a syntax tree.
	 *
	 */
	private abstract class ASTNode {

		/**
		 * Attempts to parse the remaining tokens on the token stack to a syntax tree.
		 * @return The root of the generated tree
		 * @throws ParseException If the remaining tokens can not be parsed.
		 */
		public abstract ASTNode parse() throws ParseException;

		@Override
		public abstract String toString();

		/**
		 * Converts the syntax subtree rooted at this node into
		 * the corresponding LambdaTerm object structure
		 * @param names A List containing the names of all bound variables 
		 * @param indices A List containing the indices corresponding to the bound variables' names
		 * @return The LambdaTerm created from this node's subtree.
		 * @throws ParseException If the syntax subtree could not be converted.
		 */
		public abstract LambdaTerm convert(List<String> names, List<Integer> indices) throws ParseException;
	}

	private class ASTTerm {

		public ASTNode parse() throws ParseException {
			Token activeToken = popToken();
			switch (activeToken.getType()) {
			case LBRACKET:
				ASTNode result;
				Token nextToken = peekToken();
				TokenType nextType = nextToken.getType();
				if (nextType == TokenType.LAMBDA) {
					result = new ASTAbstraction().parse();
				} else if (nextType == TokenType.NAME) {
					result = new ASTApplication().parse();
				} else if (nextType == TokenType.LBRACKET) {
					result = new ASTApplication().parse();
				} else {
					throw new ParseException("Unexpected token, " + nextType + " is not a valid term beginning" , -1, -1);
				}
				activeToken = popToken();
				if (activeToken.getType() == TokenType.RBRACKET) {
					return result;
				} else {
					throw new ParseException("", -1, -1);
				}
			case RBRACKET:
				throw new ParseException("", -1, -1);
			case NAME:
				return new ASTName(activeToken.getContent());
			case LAMBDA:
				tokens.push(activeToken);
				return new ASTAbstraction().parse();
			case DOT:
				throw new ParseException("Unexpected token DOT, expected LBRACKET, VARIABLE, NAME", -1, -1);
			default:
				throw new ParseException("Unknow token, expected LBRACKET, VARIABLE, NAME", -1, -1);
			}
		}

		public ASTNode parseFromAbstraction() throws ParseException {
			return parse();
		}

		public ASTNode parseFromApplication() throws ParseException {
			return parse();
		}
	}

	private class ASTAbstraction extends ASTNode {

		private ASTName variable;
		private ASTNode term;
		
		@Override
		public ASTNode parse() throws ParseException {
			System.out.println("ABS parse");
			if (popToken().getType() == TokenType.LAMBDA) {
				Token activeToken = popToken();
				if (activeToken.getType() == TokenType.NAME) {
					this.variable = new ASTName(activeToken.getContent());
					if (popToken().getType() == TokenType.DOT) {
						this.term = new ASTTerm().parseFromAbstraction();
						System.out.println("ABS return");
						return this;
					} else {
						throw new ParseException("Unexpected token, expected DOT", -1, -1);
					}
				} else {
					throw new ParseException("Unexpected token, expected VARIABLE", -1, -1);
				}
			} else {
				throw new ParseException("Unexpected token, expected LAMBDA", -1, -1);
			}
		}

		@Override
		public String toString() {
			return "(λ" + variable.toString() + "." + term.toString() + ")";
		}

		@Override
		public LambdaTerm convert(List<String> names, List<Integer> indices) throws ParseException {
			if (variable.isVariable() == false) {
				throw new ParseException("", -1, -1);
			}
			ArrayList<String> localNames = new ArrayList<String>(names);
			ArrayList<Integer> localIndices = new ArrayList<Integer>(indices);
			if (localIndices.isEmpty()) {
				localIndices.add(1);
				localNames.add(variable.toString());
			}
			int lastIndex = localIndices.get(localIndices.size() - 1);
			localIndices.add((lastIndex + 1));
			localNames.add(variable.toString());
			return new Abstraction(variable.toString(), term.convert(localNames, localIndices));
		}
	}

	private class ASTApplication extends ASTNode {

		private ASTNode left;
		private ASTNode right;

		@Override
		public ASTNode parse() throws ParseException {
			System.out.println("APP parse");
			this.left = new ASTTerm().parseFromApplication();
			this.right = new ASTTerm().parseFromApplication();
			System.out.println("APP return");
			return this;
		}

		@Override
		public String toString() {
			return "(" + left.toString() + " " + right.toString() + ")";
		}

		@Override
		public LambdaTerm convert(List<String> names, List<Integer> indices) throws ParseException {
			return new Application(left.convert(names, indices), right.convert(names, indices));
		}
	}

	private class ASTName extends ASTNode {

		private String name;

		public ASTName(String name) {
			System.out.println("Creating name/var: " + name);
			this.name = name;
		}

		@Override
		public ASTNode parse() throws ParseException {
			System.out.println("NAME parse");
			Token activeToken = popToken();
			if (activeToken.getType() == TokenType.NAME) {
				return new ASTName(activeToken.getContent());
			} else {
				throw new ParseException("", -1, -1);
			}
		}

		@Override
		public String toString() {
			return name;
		}

		/**
		 * Determines whether this {@link ASTName} should be converted to a
		 * variable or to a
		 * {@link edu.kit.wavelength.client.model.term.NamedTerm NamedTerm}.
		 * 
		 * @return true if this is a variable, false if it is a NamedTerm
		 */
		public boolean isVariable() {
			if (retrieveTerm(name) == null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public LambdaTerm convert(List<String> names, List<Integer> indices) {
			LambdaTerm possibleInner = retrieveTerm(name);
			if (possibleInner != null) {
				return new NamedTerm(name, possibleInner);
			} else {
				for (int i = 0; i < names.size(); i++) {
					if (this.name == names.get(i)) {
						return new BoundVariable(indices.get(i));
					}
				}
				return new FreeVariable(name);
			}
		}
	}
}
