package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the given lambda terms into plain text. This especially
 * means that the generated representation does not contain unicode symbols.
 */
public class PlaintextExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		if (displayedTerms.size() == Integer.MAX_VALUE) {
			throw new IndexOutOfBoundsException("List of displayedTerms is too big.");
		}

		PlaintextVisitor visitor = new PlaintextVisitor(Libraries.all());
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < displayedTerms.size() - 1; i++) {
			result.append("=> ");
			result.append(displayedTerms.get(i).acceptVisitor(visitor));
			result.append("\n");
		}

		// No linebreak for last LambdaTerm
		result.append("=> ");
		result.append(displayedTerms.get(displayedTerms.size() - 1).acceptVisitor(visitor));
		return result.toString();
	}

	@Override
	public String getName() {
		return "Plaintext";
	}
}
