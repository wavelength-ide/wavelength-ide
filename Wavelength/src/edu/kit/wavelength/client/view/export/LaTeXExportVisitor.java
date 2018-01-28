package edu.kit.wavelength.client.view.export;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;

/*
 * TODO Wie soll mit Variablen umgegangen werden?
 */
public class LaTeXExportVisitor extends BasicExportVisitor {

	private static final String TEXTSTART = "\\text{";
	private static final String TEXTEND = "}";

	public LaTeXExportVisitor(List<Library> libraries, String lambdaRepresentation) {
		super(libraries, lambdaRepresentation);
	}

	@Override
	public StringBuilder visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		StringBuilder result = new StringBuilder(term.getName());
		result.insert(0, TEXTSTART).append(TEXTEND);
		return result;
	}

	@Override
	public StringBuilder visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);

		StringBuilder result = new StringBuilder(app.getName());
		result.insert(0, TEXTSTART).append(TEXTEND);
		return result;
	}
}
