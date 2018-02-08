package edu.kit.wavelength.client.model.term.parsing;

import java.lang.Exception;

/**
 * An exception used to indicate an error during the parsing of an entered
 * {@link LambdaTerm}.
 *
 */
public class ParseException extends Exception {

	private final String message;
	private final int row;
	private final int columnStart;
	private final int columnEnd;

	/**
	 * Creates a new ParseException with the entered parameters.
	 * 
	 * @param message
	 *            A message to the user describing the error causing the exception
	 * @param row
	 *            The row containing the source of this exception
	 * @param column
	 *            The row containing the source of this exception
	 */
	public ParseException(String message, int row, int columnStart, int columnEnd) {
		this.message = message;
		this.row = row;
		this.columnStart = columnStart;
		this.columnEnd = columnEnd;
	}

	/**
	 * Gets the row in which the error causing this exception occurred.
	 * 
	 * @return The row in which the error occurred
	 */
	public int getRow() {
		return row + 1;
	}

	/**
	 * Gets the start column of the token in which the error causing this exception
	 * occurred.
	 * 
	 * @return The start column of the token in which the error occurred
	 */
	public int getColumnStart() {
		return columnStart + 1;
	}

	/**
	 * Gets the end column of the token in which the error causing this exception
	 * occurred.
	 * 
	 * @return The end column of the token in which the error occurred
	 */
	public int getColumnEnd() {
		return columnEnd + 1;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
