package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;

public class UpdateUnicodeOutput implements ExecutionObserver {

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!App.get().unicodeOutput().isShown()) {
			return;
		}
		// lots of formatting stuff (visitor?)
	}

}
