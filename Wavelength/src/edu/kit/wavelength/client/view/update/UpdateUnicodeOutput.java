package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!App.get().unicodeOutput().isShown()) {
			return;
		}
		// lots of formatting stuff (visitor?)
	}

}
