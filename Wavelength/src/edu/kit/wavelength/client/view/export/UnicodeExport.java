package edu.kit.wavelength.client.view.export;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the given lambda terms into text using a unicode lambda
 * letter and a unicode arrow.
 */
public class UnicodeExport implements Export {

	public static final String NAME = "Unicode";

	private static final String LAMBDA = "λ";
	private static final String ARROW = "⇒ ";

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms, List<Library> libraries) {
		Objects.requireNonNull(displayedTerms);
		Objects.requireNonNull(libraries);

		// no terms
		if (displayedTerms.size() == 0) {
			return "";
		}
		
		BasicExportVisitor visitor = new BasicExportVisitor(libraries, LAMBDA);
		StringBuilder result = new StringBuilder();

		if (displayedTerms.size() == 1) {
			return result.append(displayedTerms.get(0).acceptVisitor(visitor)).toString();
		}
		
		// No arrow for first Line
		assert (displayedTerms.size() > 1);
		result.append(displayedTerms.get(0).acceptVisitor(visitor));
		result.append("\n");

		for (int i = 1; i < displayedTerms.size() - 1; i++) {
			result.append(ARROW);
			result.append(displayedTerms.get(i).acceptVisitor(visitor));
			result.append("\n");
		}

		// No line break for last lambda term
		assert (displayedTerms.size() > 1);
		result.append(ARROW);
		result.append(displayedTerms.get(displayedTerms.size() - 1).acceptVisitor(visitor));
		return result.toString();
	}

	@Override
	public String getName() {
		return NAME;
	}
}
