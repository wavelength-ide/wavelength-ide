package edu.kit.wavelength.client.export;

import edu.kit.wavelength.client.export.Export;
import edu.kit.wavelength.client.model.ExecutionState;

/**
 * This class translates the current output into Haskell code. Since it is only
 * a syntactic translation, it is not guaranteed that the generated output is
 * executable Haskell.
 */
public class HaskellExport implements Export {

	@Override
	public String getRepresentation(ExecutionState state) {
		// format repr
		return null;
	}

}
