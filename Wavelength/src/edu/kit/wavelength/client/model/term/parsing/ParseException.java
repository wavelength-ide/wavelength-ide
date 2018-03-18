package edu.kit.wavelength.client.model.term.parsing;

import java.lang.Exception;

/**
 * An exception used to indicate an error during the parsing of an entered
 * {@link LambdaTerm}.
 *
 */
@SuppressWarnings("serial")
public class ParseException extends Exception {
	
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
	 * @param columnStart
	 *            The inclusive start column of the source of the exception
	 * @param columnEnd
	 *            The exclusive end column of the source of the exception
	 */
	public ParseException(String message, int row, int columnStart, int columnEnd) {
		super(message);
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
	 * @return The inclusive start column of the token in which the error occurred
	 */
	public int getColumnStart() {
		return columnStart + 1;
	}

	/**
	 * Gets the end column of the token in which the error causing this exception
	 * occurred.
	 * 
	 * @return The exclusive end column of the token in which the error occurred
	 */
	public int getColumnEnd() {
		return columnEnd + 1;
	}
}
