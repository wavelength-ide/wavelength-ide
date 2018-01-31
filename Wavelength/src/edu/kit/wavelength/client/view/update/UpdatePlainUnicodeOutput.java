package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;

/**
 * Observer that updates the {@link UnicodeOutput} with a new term if it is
 * displayed.
 */
public class UpdatePlainUnicodeOutput implements ExecutionObserver {
	
	private List<String> terms;
	// private App app = App.get();
	// private HTMLPanel output;
	
	public UpdatePlainUnicodeOutput() {
		terms = new ArrayList<>();
		// output = new HTMLPanel("");
	}

	@Override
	public void pushTerm(LambdaTerm t) {
		// output = new HTMLPanel("");
		// if (!app.unicodeOutput().isShown()) {
		// 	return;
		// }
		
		// TODO: libraries?
		PlainUnicodeTermVisitor visitor = new PlainUnicodeTermVisitor(new ArrayList<>());
		// System.out.println(t.acceptVisitor(visitor).toString());
		String term = t.acceptVisitor(visitor);
		terms.add(term);
		String output = String.join("<br> => ", terms);
		App.get().outputArea.clear();
		App.get().outputArea.add(new HTML(output));
		// App.get().outputArea.add(new Anchor("test"));
	}
	
	public void removeLastTerm() {
		terms.remove(terms.size() - 1);
		String output = String.join("<br> => ", terms);
		App.get().outputArea.clear();
		App.get().outputArea.add(new HTML(output));
		// TODO: remove from output
	}
	
	


}
