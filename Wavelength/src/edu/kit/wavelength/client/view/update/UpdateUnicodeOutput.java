package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;
import edu.kit.wavelength.client.view.webui.component.UnicodeTerm;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {
	
	private App app = App.get();

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!app.unicodeOutput().isShown()) {
			return;
		}
		// TODO: libraries?
		UnicodeTerm term = t.acceptVisitor(new UnicodeTermVisitor(null));
		app.unicodeOutput().setTerm(term);
	}


}
