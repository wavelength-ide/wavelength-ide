package edu.kit.wavelength.client.model.term.parsing;

import java.lang.Exception;

/**
 * An exception used to indicate an error during the parsing of an entered {@link LambdaTerm}.
 *
 */
public class ParseException extends Exception {

	private final String message;
	private final int row;
	private final int column; 
	
	/**
	 * Creates a new ParseException with the entered parameters.
	 * @param message A message to the user describing the error causing the exception
	 * @param row The row containing the source of this exception
	 * @param column The row containing the source of this exception
	 */
	public ParseException(String message, int row, int column) {
		this.message = message;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Gets the row in which the error causing this exception occurred.
	 * @return The row in which the error occurred
	 */
	public int getRow() {
		return row + 1;
	}
	
	/**
	 * Gets the column in which the error causing this exception occurred.
	 * @return The column in which the error occurred
	 */
	public int getColumn() {
		return column + 1;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
