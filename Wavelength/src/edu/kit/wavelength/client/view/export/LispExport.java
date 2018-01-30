package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the given lambda terms into Lisp code. Since it is only
 * a syntactic translation, it is not guaranteed that the generated output is
 * executable Lisp.
 */
public class LispExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		if (displayedTerms.size() == Integer.MAX_VALUE) {
			throw new IndexOutOfBoundsException("List of displayedTerms is too big.");
		}

		//no terms
		if (displayedTerms.size() == 0) {
			return "";
		}
		
		LispExportVisitor visitor = new LispExportVisitor(Libraries.all());
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayedTerms.size() - 1; i++) {
			result.append(displayedTerms.get(i).acceptVisitor(visitor));
			result.append("\n");
		}

		// No line break for last lambda term
		assert (displayedTerms.size() >= 1);
		result.append(displayedTerms.get(displayedTerms.size() - 1).acceptVisitor(visitor));
		return result.toString();
	}

	@Override
	public String getName() {
		return "Lisp";
	}
}
