package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class represents a tuple of a {@link LambdaTerm} and the
 * {@link OutputFormat} that was selected at the time of printing the term.
 */
public class TermFormatTuple {

	public LambdaTerm term;
	public OutputFormat format;

	/**
	 * Creates a new tuple by taking a LambdaTerm and a format.
	 */
	public TermFormatTuple(LambdaTerm term, OutputFormat format) {
		this.term = term;
		this.format = format;
	}

}
