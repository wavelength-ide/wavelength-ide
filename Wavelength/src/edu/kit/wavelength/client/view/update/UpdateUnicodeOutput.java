package edu.kit.wavelength.client.view.update;

import com.apple.eawt.Application;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;
import edu.kit.wavelength.client.view.webui.component.UnicodeTerm;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!App.get().unicodeOutput().isShown()) {
			return;
		}
		// TODO: libraries?
		UnicodeTerm term = t.acceptVisitor(new UnicodeTermVisitor(null));
		App.get().unicodeOutput().setTerm(term);
	}


}
