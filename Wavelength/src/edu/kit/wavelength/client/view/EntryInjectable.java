package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.action.Action;

/**
 * This interface is intended to make Menus able to add individual entries that have their own {@link Action} when clicked.
 */
public interface EntryInjectable {

	/**
	 * Adds an entry.
	 * 
	 * @param name name of the entry
	 * @param action action that this entry can invoke
	 */
	void addEntry(final String name, final Action action);
}
