package edu.kit.wavelength.client.model.term.parsing;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import edu.kit.wavelength.client.model.library.CustomLibrary;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

public class Parser {

	private final List<Library> loadedLibraries;
	private CustomLibrary inputLibrary;
	private Stack<Token> tokens;
	private RegExp assignmentRegExp = RegExp.compile("\\s*[a-zA-Z0-9]+\\s*=\\s*.+\\s*");

	private int rowPos = 0;
	private int columnPos = 0;

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
		String[] possibleRows = input.split("\n");

		ArrayList<String> rows = new ArrayList<String>();
		for (int i = 0; i < possibleRows.length; i++) {
			if (possibleRows[i] == "") {
				break;
			}
			RegExp blankEx = RegExp.compile("\\s*");
			RegExp comEx = RegExp.compile("\\s*--.*");
			MatchResult blankResult = RegExp.compile("^\\s+^[.]").exec(possibleRows[i]);
			MatchResult commentResult = RegExp.compile("^\\s*--.*").exec(possibleRows[i]);
			if (blankResult == null && commentResult == null) {
				rows.add(possibleRows[i]);
			}
		}
		for (int i = 0; i < (rows.size() - 1); i++) {
			rowPos = i;
			readLibraryTerm(rows.get(i));
		}
		rowPos = rows.size() - 1;
		String lastLine = rows.get((rows.size() - 1));
		return parseTerm(lastLine); 
	}

	private void readLibraryTerm(String input) throws ParseException {
		MatchResult assign = assignmentRegExp.exec(input);
		if (assign != null) {
			String[] split = input.split("=");
			String name = split[0].trim();
			String termString = split[1].trim();
			LambdaTerm term = parseTerm(termString);
			inputLibrary.addTerm(term, name);
		} else {
			throw new ParseException("Not a valid name assignment", rowPos, columnPos);
		}
	}

	/**
	 * Returns the topmost token on the stack without removing it.
	 * 
	 * @return The topmost token on the stack, null if the stack is empty
	 */
	private Token peekToken() {
		try {
			return tokens.peek();
		} catch (EmptyStackException e) {
			return null;
		}
	}

	/**
	 * Pops a token from the token Stack.
	 * 
	 * @return The topmost token on the stack
	 */
	private Token popToken() {
		Token pop = tokens.pop();
		columnPos = columnPos + pop.getContent().length();
		return pop;
	}

	private void pushToken(Token newToken) {
		columnPos = columnPos - newToken.getContent().length();
		tokens.push(newToken);
	}

	/**
	 * Retrieves the term with the specified name from the loaded libraries or
	 * the library containing the user's named terms.
	 * 
	 * @param name
	 *            The desired term's name
	 * @return The term with the assigned name, null if no loaded library
	 *         contains a term with this name.
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
		columnPos = 0;
		tokens = new Stack<Token>();
		for (int i = (tokenisedInput.length - 1); i >= 0; i--) {
			tokens.add(tokenisedInput[i]);
		}
		ASTCollector root = new ASTCollector();
		FormattedASTNode formattedRoot = root.format();
		if (tokens.isEmpty() == false) {
			throw new ParseException("Unable to parse all tokens", rowPos, columnPos);
		}
		return formattedRoot.convert(new ArrayList<String>());
	}

	/**
	 * The abstract superclass of all classes representing nodes of a syntax
	 * tree.
	 *
	 */
	private interface ASTNode {

		/**
		 * Attempts to parse the remaining tokens on the token stack to a syntax
		 * tree.
		 * 
		 * @return The root of the generated tree
		 * @throws ParseException
		 *             If the remaining tokens can not be parsed.
		 */
		public abstract void parse() throws ParseException;

		public abstract FormattedASTNode format() throws ParseException;
	}

	private interface FormattedASTNode extends ASTNode {
		@Override
		public abstract String toString();

		/**
		 * Converts the syntax subtree rooted at this node into the
		 * corresponding LambdaTerm object structure
		 * 
		 * @param names
		 *            A List containing the names of all bound variables
		 * @return The LambdaTerm created from this node's subtree.
		 * @throws ParseException
		 *             If the syntax subtree could not be converted.
		 */
		public abstract LambdaTerm convert(List<String> names) throws ParseException;
	}

	private class ASTCollector implements ASTNode {

		private List<ASTNode> childNodes;

		public ASTCollector() throws ParseException {
			childNodes = new ArrayList<ASTNode>();
			parse();
		}

		@Override
		public void parse() throws ParseException {
			Token activeToken = popToken();
			boolean expectBracket = false;
			if (activeToken.getType() == TokenType.LBRACKET) {
				expectBracket = true;
			}
			// ( check TODO
			pushToken(activeToken);
			int loop = 0;
			boolean foundEnd = false;
			while (tokens.isEmpty() == false && foundEnd == false) {
				activeToken = popToken();					
				switch (activeToken.getType()) {
				case LBRACKET:
					TokenType peekType = peekToken().getType();
					switch (peekType) {
					case LBRACKET:
						this.childNodes.add(new ASTCollector());
						break;
					case RBRACKET:
						throw new ParseException("", rowPos, columnPos);
					case LAMBDA:
						if (loop < 1) {
							expectBracket = false;
						}
						pushToken(activeToken);
						this.childNodes.add(new ASTProtoAbstraction());
						break;
					case NAME:
						this.childNodes.add(new ASTName());
						break;
					case DOT:
						throw new ParseException("", rowPos, columnPos);
					default:
						throw new ParseException("", rowPos, columnPos);
					}
					break;
				case RBRACKET:
					if (expectBracket == true) {
						foundEnd = true;
						break;
					} else {
						pushToken(activeToken);
						return;
					}
				case LAMBDA:
					pushToken(activeToken);
					this.childNodes.add(new ASTProtoAbstraction());
					break;
				case NAME:
					pushToken(activeToken);
					this.childNodes.add(new ASTName());
					break;
				case DOT:
					throw new ParseException("", rowPos, columnPos);
				default:
					throw new ParseException("", rowPos, columnPos);
				}
			}

		}

		@Override
		public FormattedASTNode format() throws ParseException {
			if (childNodes == null || childNodes.isEmpty()) {
				throw new ParseException("", rowPos, columnPos);
			}
			FormattedASTNode result;
			if (childNodes.size() == 1) {
				result = childNodes.get(0).format();
			} else {
				result = new ASTApplication(childNodes.remove(0).format(), childNodes.remove(0).format());
				while (childNodes.isEmpty() == false) {
					result = new ASTApplication(result, childNodes.remove(0).format());
				}
			}
			return result;
		}
	}

	private class ASTProtoAbstraction implements ASTNode {

		private ASTName variable;
		private ASTCollector termCollector;

		public ASTProtoAbstraction() throws ParseException {
			parse();
		}

		@Override
		public void parse() throws ParseException {
			Token activeToken = popToken();
			boolean expectBracket = false;
			if (activeToken.getType() == TokenType.LBRACKET) {
				expectBracket = true;
				activeToken = popToken();
			}
			if (activeToken.getType() == TokenType.LAMBDA) {
				activeToken = popToken();
				if (activeToken.getType() == TokenType.NAME) {
					this.variable = new ASTName(activeToken.getContent());
					activeToken = popToken();
					if (activeToken.getType() == TokenType.DOT) {
						this.termCollector = new ASTCollector();
						if (expectBracket && popToken().getType() != TokenType.RBRACKET) {
							throw new ParseException("", rowPos, columnPos);
						}
						return;
					}
				}
			}
			throw new ParseException("", rowPos, columnPos);
		}

		@Override
		public FormattedASTNode format() throws ParseException {
			return new ASTAbstraction(variable, termCollector.format());
		}

	}

	private class ASTApplication implements FormattedASTNode {

		private FormattedASTNode left;
		private FormattedASTNode right;

		public ASTApplication(FormattedASTNode left, FormattedASTNode right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public void parse() throws ParseException {
			return;
		}

		@Override
		public String toString() {
			return "(" + left.toString() + " " + right.toString() + ")";
		}

		@Override
		public LambdaTerm convert(List<String> names) throws ParseException {
			return new Application(left.convert(names), right.convert(names));
		}

		@Override
		public FormattedASTNode format() throws ParseException {
			return this;
		}

	}

	private class ASTAbstraction implements FormattedASTNode {

		private ASTName variable;
		private FormattedASTNode term;

		public ASTAbstraction(ASTName variable, FormattedASTNode term) {
			this.variable = variable;
			this.term = term;
		}

		@Override
		public String toString() {
			return "(λ" + variable + "." + term.toString() + ")";
		}

		@Override
		public void parse() throws ParseException {
			return;
		}

		@Override
		public FormattedASTNode format() throws ParseException {
			return this;
		}

		@Override
		public LambdaTerm convert(List<String> names) throws ParseException {
			ArrayList<String> newNames = new ArrayList<String>(names);
			String varString = variable.toString();
			for (int i = 0; i < varString.length(); i++) {
				newNames.add(0,Character.toString(varString.charAt(i)));
			}
			String lastChar = Character.toString(varString.charAt(varString.length() - 1));
			Abstraction abs = new Abstraction(lastChar, term.convert(newNames));
			for (int i = varString.length() - 2; i >= 0; i--) {
				abs = new Abstraction(Character.toString(varString.charAt(i)), abs);
			}
			return abs;
		}

	}

	private class ASTName implements FormattedASTNode {

		private String name;
		
		public ASTName() throws ParseException {
			parse();
		}
		
		public ASTName(String name) {
			this.name = name;
		}

		@Override
		public void parse() throws ParseException {
			Token activeToken = popToken();
			if (activeToken.getType() == TokenType.NAME) {
				this.name = activeToken.getContent();
			} else {
				throw new ParseException("", -1, -1);
			}

		}

		public boolean isNamedTerm() {
			if (retrieveTerm(name) != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public LambdaTerm convert(List<String> names) throws ParseException {
			if (this.isNamedTerm()) {
				return new NamedTerm(name, retrieveTerm(name));
			} else {
				if (name.length() > 1) {
					throw new ParseException("", rowPos, columnPos);
				} else {
					if (names.contains(name)) {
						return new BoundVariable(names.indexOf(name) + 1);
					} else {
						return new FreeVariable(name);
					}
				}
			}
		}

		@Override
		public FormattedASTNode format() throws ParseException {
			return this;
		}

	}
}
