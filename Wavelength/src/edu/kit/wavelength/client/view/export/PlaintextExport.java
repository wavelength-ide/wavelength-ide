package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the current output into plain text.
 */
public class PlaintextExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "Plaintext";
	}

}
