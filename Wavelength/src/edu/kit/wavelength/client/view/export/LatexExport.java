package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the given lambda terms into LaTeX code. The generated
 * representation assumes math mode when being pasted into an existing LaTeX
 * document.
 */
public class LatexExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "LaTeX";
	}

}
