package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public class TermFormatTuple {
	
	public LambdaTerm term;
	public OutputFormat format;
	
	public TermFormatTuple(LambdaTerm term, OutputFormat format) {
		this.term = term;
		this.format = format;
	}

}
