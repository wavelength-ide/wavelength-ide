package edu.kit.wavelength.client.view.webui.components;

import java.io.IOException;
import java.nio.CharBuffer;

import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * A OptionBox is an adapter class for a GWT ListBox.
 * 
 * It provides a means for the User to set Options for a calculation. This Box
 * can be blocked and unblocked if changing the Options must not be possible.
 */
public class OptionBox implements Deactivatable, EntryInjectable, Readable {

	/**
	 * Constructs a new and empty OptionBox.
	 * 
	 * @param listBox
	 *            The GWT ListBox that this class wraps.
	 */
	public OptionBox(ListBox listBox) {

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

	}

	@Override
	public void addEntry(String name, Action action) {

	}

	@Override
	public String read() {
		// TODO Auto-generated method stub
		return null;
	}

}
