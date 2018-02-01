package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;

/**
 * This class is a visitor to translate a lambda term into a string using
 * Haskell syntax. However it is not guaranteed that the generated
 * representation is executable Haskell code.
 */
public class HaskellExportVisitor extends BasicExportVisitor {

	private static final String LAMBDA = "\\";
	private static final String ARROW = " -> ";

	public HaskellExportVisitor(List<Library> libraries) {
		super(libraries, LAMBDA);
	}

	@Override
	protected StringBuilder formatLambda(StringBuilder absVariable) {
		assert (absVariable != null);

		return absVariable.insert(0, LAMBDA).append(ARROW);
	}
}
