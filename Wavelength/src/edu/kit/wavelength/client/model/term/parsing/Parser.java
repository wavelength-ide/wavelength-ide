package edu.kit.wavelength.client.model.term.parsing;

import edu.kit.wavelength.client.model.term.LambdaTerm;

import edu.kit.wavelength.client.model.library.Library;

import java.util.List;

/**
 * This class is used to convert an input String into a {@link LambdaTerm} object. If
 * any {@link Libraries} terms are used in the input, the necessary {@link Libraries} have to be
 * passed to the Parser through its constructor.
 *
 */
public class Parser {

	private final List<Library> loadedLibraries;
	private Library inputLibrary;
	
	/**
	 * Initializes a new parser.
	 * 
	 * @param libraries
	 *            The {@link Libraries} to be taken into consideration during parsing
	 */
	public Parser(List<Library> libraries) {
		this.loadedLibraries = libraries;
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
		return null;
	}
	
	/**
	 * Gets a library containing the lambda terms and corresponding names defined in the the last invocation of {@link #parse(String)}}'s input String.
	 * @return A {@link Library} containing the terms entered by the user with their assigned names
	 */
	public Library getInputLibary() {
		return inputLibrary;
	}
}
