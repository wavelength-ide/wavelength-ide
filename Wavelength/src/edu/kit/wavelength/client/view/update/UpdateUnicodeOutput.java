package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {
	
	private List<LambdaTerm> terms;
	private App app = App.get();
	private HTMLPanel output;
	
	public UpdateUnicodeOutput() {
		terms = new ArrayList<>();
		output = new HTMLPanel("");
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		if (!app.unicodeOutput().isShown()) {
			return;
		}
		
		terms.add(t);
		// TODO: libraries?
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(null);
		HTML term = t.acceptVisitor(visitor);
		output.add(term);
	}
	
	public void removeLastTerm() {
		terms.remove(terms.size() - 1);
		// TODO: remove from output
	}
	
	


}
