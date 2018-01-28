package edu.kit.wavelength.client.view.webui.component;

import java.util.List;
import com.google.gwt.user.client.ui.TextArea;



import edu.kit.wavelength.client.view.api.Output;

/**
 * Displays lambda terms in tree format.
 */
public class TreeOutput implements Output {
	
	private TextArea output;
	private List<TreeTerm> terms;
	
	public TreeOutput(TextArea output) {
		this.output = output;
	}

	@Override
	public void lock() {
		output.setEnabled(false);
	}

	@Override
	public void unlock() {
		output.setEnabled(true);
	}

	@Override
	public void hide() {
		output.setVisible(false);
	}

	@Override
	public void show() {
		output.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return output.isVisible();
	}

	@Override
	public boolean isLocked() {
		return output.isEnabled();
	}

	@Override
	public void write(String input) {
		output.setText(input);
	}
	
	public void clear() {
		write("");
		terms.clear();
	}

	@Override
	public void removeLastTerm() {
		if (!terms.isEmpty()) {
			terms.remove(terms.size() - 1);
		}
		// TODO: remove term from display, how?
	}

	

	public void setTerm(TreeTerm term) {
		terms.add(term);
		// TODO: term-baum traversieren, layouten
	}
}
