package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * A OptionBox is an adapter class for a GWT ListBox.
 * 
 * It provides a means for the User to set Options for a calculation. This Box
 * can be blocked and unblocked if changing the Options must not be possible.
 */
public class OptionBox implements Clickable, Hideable, Lockable, Readable, Serializable {

	/**
	 * Constructs a new and empty OptionBox.
	 * 
	 * @param listBox
	 *            the wrapped {@link ListBox}
	 */
	public OptionBox(final ListBox listBox) {

	}

	public static OptionBox deserialize(String serialized) {
		return null;
	}

	@Override
	public void lock() {

	}

	@Override
	public void unlock() {

	}

	@Override
	public String read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isShown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAction(Action action) {
		// TODO Auto-generated method stub
		
	}

}
