package edu.kit.wavelength.client.model.term.parsing;

import java.lang.Exception;

/**
 * An exception used to indicate an error during the parsing of an entered lambda term.
 *
 */
public class ParseException extends Exception {

	/**
	 * Creates a new ParseException with the entered parameters.
	 * @param message A message to the user describing the error causing the exception
	 * @param row The row containing the source of this exception
	 * @param column The row containing the source of this exception
	 */
	public ParseException(String message, int row, int column) {
		
	}
	
	/**
	 * Gets the row in which the error causing this exception occurred.
	 * @return The row in which the error occurred
	 */
	public int getRow() {
		return 0;
	}
	
	/**
	 * Gets the column in which the error causing this exception occurred.
	 * @return The column in which the error occurred
	 */
	public int getColumn() {
		return 0;
	}
	
}
