package edu.kit.wavelength.client.view.api;

public interface Output extends Lockable, Hideable, Writable {
	void removeLastTerm();
}
