package edu.kit.wavelength.client.view.webui.component;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * Displays lambda terms in tree format.
 */
public class TreeOutput implements Lockable, Hideable, Writable {

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

	public void removeLastTerm() {

	}
}
