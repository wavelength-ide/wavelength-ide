package edu.kit.wavelength.client.view.webui.component;

import java.util.List;

import edu.kit.wavelength.client.view.api.Output;

/**
 * Displays lambda terms in text format with unicode symbols.
 */
public class UnicodeOutput implements Output {
	
	private List<UnicodeTerm> terms;

	@Override
	public void lock() {		
	}

	@Override
	public void unlock() {		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public boolean isShown() {
		return false;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public void write(String input) {
		
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
	
	public void setTerm(UnicodeTerm term) {
		write(term.getRepresentation());
	}

}
