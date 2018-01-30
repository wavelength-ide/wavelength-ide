package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;

public class LispExportVisitor extends BasicExportVisitor {

	private static final String LAMBDA = "lambda";

	public LispExportVisitor(List<Library> libraries) {
		super(libraries, "");
	}

	@Override
	protected StringBuilder formatLambda(StringBuilder absVariable) {
		assert (absVariable != null);

		absVariable.insert(0, " (").append(") ");
		return absVariable.insert(0, LAMBDA);
	}
}
