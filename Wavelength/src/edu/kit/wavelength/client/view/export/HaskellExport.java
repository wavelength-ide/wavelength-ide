package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.export.Export;

/**
 * This class translates the given lambda terms into Haskell code. Since it is
 * only a syntactic translation, it is not guaranteed that the generated
 * representation is executable Haskell code.
 */
public class HaskellExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "Haskell";
	}

}
