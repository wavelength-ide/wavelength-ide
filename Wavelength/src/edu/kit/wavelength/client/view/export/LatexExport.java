package edu.kit.wavelength.client.view.export;

import edu.kit.wavelength.client.model.ExecutionState;

/**
 * This class translates the current output into LaTeX code.
 */
public class LatexExport implements Export {

	@Override
	public String getRepresentation(ExecutionState state) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "LaTeX";
	}

}
