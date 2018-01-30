package edu.kit.wavelength.client.view.export;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;

/**
 * This class is a visitor to translate a lambda term into a string using
 * LaTeX syntax.
 */
public class LaTeXExportVisitor extends BasicExportVisitor {

	private static final String TEXTSTART = "\\text{";
	private static final String TEXTEND = "}";
	private static final String LAMBDA = "\\lambda ";

	public LaTeXExportVisitor(List<Library> libraries) {
		super(libraries, LAMBDA);
	}

	@Override
	protected StringBuilder formatText(StringBuilder text) {
		Objects.requireNonNull(text);

		return text.insert(0, TEXTSTART).append(TEXTEND);
	}
}
