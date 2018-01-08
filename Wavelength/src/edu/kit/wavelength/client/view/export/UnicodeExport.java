package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public class UnicodeExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "Unicode";
	}

}
