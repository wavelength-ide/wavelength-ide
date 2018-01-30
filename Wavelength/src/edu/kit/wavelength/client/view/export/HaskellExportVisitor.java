package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;

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
