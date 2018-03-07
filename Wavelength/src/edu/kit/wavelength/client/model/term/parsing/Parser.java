package edu.kit.wavelength.client.model.term.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

/**
 * This class is used to convert an input String into a {@link LambdaTerm}
 * object. If any {@link Libraries} terms are used in the input, the necessary
 * {@link Libraries} have to be passed to the Parser through its constructor.
 *
 */
public class Parser {

	private final List<Library> loadedLibraries;
	private CustomLibrary inputLibrary;
	private Token[] tokens;
	private RegExp assignmentRegExp = RegExp.compile("^\\s*[a-zA-Z0-9]+\\s*=\\s*[^=]+\\s*$");
	private ArrayList<String> boundVariables;
	private int rowPos = 1;

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
	 * Gets a library containing the lambda terms and corresponding names defined in
	 * the the last invocation of {@link #parse(String)}}'s input String.
	 * 
	 * @return A {@link Library} containing the terms entered by the user with their
	 *         assigned names
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
		Objects.requireNonNull(input);
		
		String[] possibleRows = input.split("\n");

		ArrayList<Integer> rows = new ArrayList<Integer>();
		for (int i = 0; i < possibleRows.length; i++) {
			if (possibleRows[i] == "") {
				continue;
			}

			// Remove comments
			String withDummyComment = possibleRows[i] + " --";
			possibleRows[i] = withDummyComment.substring(0, withDummyComment.indexOf("--"));

			// Only consider lines that contain something interesting
			MatchResult blankResult = RegExp.compile("^\\s*$").exec(possibleRows[i]);
			if (blankResult == null) {
				rows.add(i);
			}
		}

		// All but the last row has to be a named term
		for (int i = 0; i < (rows.size() - 1); i++) {
			rowPos = rows.get(i);
			readLibraryTerm(possibleRows[rows.get(i)]);
		}
		
		if (rows.isEmpty())
			throw new ParseException("input must contain a lambda term", 0, 0, 1);

		// Final row is the actual term that we are looking for
		rowPos = rows.get(rows.size() - 1);
		String lastLine = possibleRows[rows.get((rows.size() - 1))];
		return parseTerm(lastLine, 0);
	}

	private void readLibraryTerm(String input) throws ParseException {
		MatchResult assign = assignmentRegExp.exec(input);
		if (assign != null) {
			String[] split = input.split("=");
			String name = split[0].trim();
			String termString = split[1].trim();
			LambdaTerm term = parseTerm(termString, input.length() - termString.length());
			inputLibrary.addTerm(term, name);
		} else {
			throw new ParseException("\"" + input + "\" is not a valid name assignment", rowPos, 0, 0);
		}
	}

	/**
	 * Retrieves the term with the specified name from the loaded libraries or the
	 * library containing the user's named terms.
	 * 
	 * @param name
	 *            The desired term's name
	 * @return The term with the assigned name, null if no loaded library contains a
	 *         term with this name.
	 */
	private LambdaTerm retrieveTerm(String name) {
		if (inputLibrary != null && inputLibrary.containsName(name)) {
			return inputLibrary.getTerm(name).clone();
		}
		for (Library currentLibrary : loadedLibraries) {
			if (currentLibrary.containsName(name)) {
				return currentLibrary.getTerm(name).clone();
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
	private LambdaTerm parseTerm(String input, int offset) throws ParseException {
		tokens = new Tokeniser().tokenise(input, offset, rowPos);
		boundVariables = new ArrayList<>();
		
		if (tokens.length == 0)
			throw new ParseException("The empty term is not a lambda term", rowPos, 0, 1);
		
		return parseLambdaTerm(0, tokens.length);
	}

	// Parse a lambda term at position [left, right)
	private LambdaTerm parseLambdaTerm(int left, int right) throws ParseException {
		if (left == right)
			throw new ParseException("The empty term is not a lambda term", rowPos, tokens[left].getStart(),
					tokens[left].getEnd());

		ArrayList<LambdaTerm> terms = new ArrayList<>();

		while (left != right) {
			RangedTerm term = parseNonApplication(left, right);

			left = term.getRight();
			terms.add(term.getTerm());
		}

		// We have now a bunch of terms à la "x x x" and need to build (x x) x out of it
		LambdaTerm result = terms.get(0);
		for (int i = 1; i < terms.size(); ++i) {
			result = new Application(result, terms.get(i));
		}

		return result;
	}

	// Find the matching bracket for the lbracket at left, as long as it is left of
	// right.
	private int findMatching(int left, int right) throws ParseException {
		int diff = 0;
		for (int i = left; i < right; ++i) {
			if (tokens[i].getType() == TokenType.LBRACKET)
				++diff;
			if (tokens[i].getType() == TokenType.RBRACKET)
				--diff;

			if (diff == 0)
				return i;
		}
		throw new ParseException("Unbalanced parentheses.", rowPos, tokens[left].getStart(), tokens[left].getEnd());
	}

	private RangedTerm parseNonApplication(int left, int right) throws ParseException {
		if (left == right)
			throw new ParseException("The empty term is not a lambda term", rowPos, tokens[left].getStart(),
					tokens[left].getEnd());

		// Since we are explicitly not looking for an application, we greedily know what
		// to do

		switch (tokens[left].getType()) {
		case LBRACKET: // Some lambda term surrounded by brackets
			int matching = findMatching(left, right);
			return new RangedTerm(matching + 1, parseLambdaTerm(left + 1, matching));

		case LAMBDA: // An abstraction
			if (right <= left + 3 || tokens[left + 1].getType() != TokenType.NAME
					|| tokens[left + 2].getType() != TokenType.DOT)
				throw new ParseException("Malformed lambda expression", rowPos, tokens[left].getStart(),
						tokens[left].getEnd());

			// Register the variable that we are binding
			boundVariables.add(tokens[left + 1].getContent());
			LambdaTerm result = new Abstraction(tokens[left + 1].getContent(), parseLambdaTerm(left + 3, right));
			boundVariables.remove(boundVariables.size() - 1);

			return new RangedTerm(right, result);

		case NAME: // Either a bound variable, or a named term, or a free variable
			String looking = tokens[left].getContent();
			// Need to find the innermost bound variable, so go from high to low
			for (int i = boundVariables.size() - 1; i >= 0; --i) {
				if (boundVariables.get(i).equals(looking))
					return new RangedTerm(left + 1, new BoundVariable(boundVariables.size() - i));
			}

			// Name is not bound by abstraction, try to find it as a named term from some
			// library
			LambdaTerm retrieved = retrieveTerm(looking);
			if (retrieved != null)
				return new RangedTerm(left + 1, new NamedTerm(looking, retrieved));
			else
				return new RangedTerm(left + 1, new FreeVariable(looking));

		default:
			throw new ParseException("Unexpected token: " + tokens[left].getType().toString(), rowPos,
					tokens[left].getStart(), tokens[left].getEnd());
		}
	}

	// A term that extends to right-1 in the token array
	private static class RangedTerm {
		private int right;
		private LambdaTerm term;

		public RangedTerm(int right, LambdaTerm term) {
			this.right = right;
			this.term = term;
		}

		public int getRight() {
			return right;
		}

		public LambdaTerm getTerm() {
			return term;
		}
	}

}
