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

	/**
	 * Creates a new OptionBox from the specified serialized string.
	 * @param serialized serialized string, as created in serialize
	 * @return new OptionBox
	 */
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
		return null;
	}

	@Override
	public String serialize() {
		return null;
	}

	@Override
	public boolean isLocked() {
		return false;
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
	public void setAction(Action action) {
		
	}

}
