package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/*
 * TODO: definitionen von Termen mit "\text{...}" unklammern?
 */
/**
 * This class translates the given lambda terms into LaTeX code. The generated
 * representation assumes math mode when being pasted into an existing LaTeX
 * document.
 */
public class LatexExport implements Export {


	private static final String LAMBDA = "\\lambda";
	private static final String ARROW = "\\Rightarrow ";
	
	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		if (displayedTerms.size() == Integer.MAX_VALUE) {
			throw new IndexOutOfBoundsException("List of displayedTerms is too big.");
		}

		//no terms
		if (displayedTerms.size() == 0) {
			return "";
		}
		
		BasicExportVisitor visitor = new BasicExportVisitor(Libraries.all(), LAMBDA);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayedTerms.size() - 1; i++) {
			result.append("$");
			result.append(ARROW);
			result.append(displayedTerms.get(i).acceptVisitor(visitor));
			result.append("$");
			//append a LaTeX line break
			result.append("\\\\");
			//append a Java line break
			result.append("\n");
		}

		// No line break for last lambda term
		assert (displayedTerms.size() >= 1);
		result.append("$");
		result.append(ARROW);
		result.append(displayedTerms.get(displayedTerms.size() - 1).acceptVisitor(visitor));
		result.append("$");
		return result.toString();
	}

	@Override
	public String getName() {
		return "LaTeX";
	}
}
