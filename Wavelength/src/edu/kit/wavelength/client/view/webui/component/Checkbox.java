package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.CheckBox;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Toggleable;

/**
 * This class wraps GWT's {@link CheckBox}.
 */
public class Checkbox implements Readable, Hideable, Toggleable, Serializable, Lockable {

	private CheckBox wrappedCheckBox;

	/**
	 * Creates a new check box
	 * 
	 * @param checkBox
	 *            the wrapped {@link CheckBox}
	 * @param name
	 *            the text shown next to the check box
	 */
	public Checkbox(final CheckBox checkBox, final String name) {
		wrappedCheckBox = checkBox;
		wrappedCheckBox.setName(name);
	}

	/**
	 * Creates a new check box from the specified serialized string.
	 * 
	 * @param serialized
	 *            serialized string, as created in serialize
	 * @return new check box
	 */
	public static Checkbox deserialize(String serialized) {
		// TODO implement method
		return null;
	}

	/**
	 * Determines whether this check box is currently set.
	 * 
	 * @return true if the check box is checked and false otherwise.
	 */
	@Override
	public boolean isSet() {
		return wrappedCheckBox.getValue();
	}

	@Override
	public String read() {
		return wrappedCheckBox.getName();
	}

	@Override
	public String serialize() {
		// TODO implement method
		return null;
	}

	@Override
	public void lock() {
		wrappedCheckBox.setEnabled(false);
	}

	@Override
	public void unlock() {
		wrappedCheckBox.setEnabled(true);
	}

	@Override
	public boolean isLocked() {
		return wrappedCheckBox.isEnabled();
	}

	@Override
	public void set() {
		wrappedCheckBox.setValue(true);
	}

	@Override
	public void unset() {
		wrappedCheckBox.setValue(false);
	}

	@Override
	public void hide() {
		wrappedCheckBox.setVisible(false);
	}

	@Override
	public void show() {
		wrappedCheckBox.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return wrappedCheckBox.isVisible();
	}

}
