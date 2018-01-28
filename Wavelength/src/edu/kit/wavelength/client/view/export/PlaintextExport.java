package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the given lambda terms into plain text. This especially
 * means that the generated representation does not contain unicode symbols.
 */
public class PlaintextExport implements Export {

	private static final String LAMBDA = "\\";
	private static final String ARROW = "=> ";
	
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
			result.append(ARROW);
			result.append(displayedTerms.get(i).acceptVisitor(visitor));
			result.append("\n");
		}

		// No line break for last lambda term
		assert (displayedTerms.size() >= 1);
		result.append(ARROW);
		result.append(displayedTerms.get(displayedTerms.size() - 1).acceptVisitor(visitor));
		return result.toString();
	}

	@Override
	public String getName() {
		return "Plaintext";
	}
}
