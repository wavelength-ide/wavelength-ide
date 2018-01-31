package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;


import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdateUnicodeOutput implements ExecutionObserver {
	
	private List<FlowPanel> terms;
	private static App app = App.get();
	
	public UpdateUnicodeOutput() {
		terms = new ArrayList<>();
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		// if (!app.unicodeOutput().isShown()) {
		// 	return;
		// }
		
		// TODO: libraries?
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(new ArrayList<>());
		Tuple term = t.acceptVisitor(visitor);
		terms.add(term.panel);
		FlowPanel output = new FlowPanel("div");
		output.add(term.panel);
		app.outputArea.add(output);
	}
	
	public void removeLastTerm() {
		terms.remove(terms.size() - 1);
		// TODO: probably not fast enough
		app.outputArea.clear();
		for (FlowPanel term : terms) {
			FlowPanel wrap = new FlowPanel("div");
			wrap.add(term);
			app.outputArea.add(wrap);
		}
		
	}
	
	


}
