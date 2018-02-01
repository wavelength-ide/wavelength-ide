package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
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
		// TODO: libraries?
		UnicodeTermVisitor visitor = new UnicodeTermVisitor(new ArrayList<>());
		Tuple term = t.acceptVisitor(visitor);
		app.outputArea().clear();
		for(int i = 0; i < terms.size(); i++) {
			FlowPanel wrapper = new FlowPanel();
			wrapper.addStyleName("nonclickable");
			wrapper.add(terms.get(i));
			app.outputArea().add(wrapper);
		}
		
		terms.add(term.panel);
		// FlowPanel output = new FlowPanel("div");
		// output.add(term.panel);
		app.outputArea().add(term.panel);
	}
	
	public void removeLastTerm() {
		if(terms.isEmpty()) {
			return;
		}
		terms.remove(terms.size() - 1);
		// TODO: probably not fast enough
		app.outputArea().clear();
		for (int i = 0; i < terms.size() - 1; i++) {
			FlowPanel wrap = new FlowPanel("div");
			wrap.addStyleName("nonclickable");
			wrap.add(terms.get(i));
			app.outputArea().add(wrap);
		}
		FlowPanel wrap = new FlowPanel("div");
		wrap.addStyleName("againClickable");
		wrap.add(terms.get(terms.size()-1));
		app.outputArea().add(wrap);		
	}
	
	


}
