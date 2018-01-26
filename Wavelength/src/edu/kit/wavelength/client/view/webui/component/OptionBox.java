package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * An OptionBox is an adapter class for a GWT {@link ListBox}.
 * 
 * It provides a means for the User to set Options for a calculation. This Box
 * can be locked and unlocked if changing the Options must not be possible.
 */
public class OptionBox implements Hideable, Lockable, Readable, Serializable, Clickable {

	private ListBox wrappedListBox;
	private HandlerRegistration currentEvent;

	/**
	 * Constructs a new and empty OptionBox.
	 * 
	 * @param listBox
	 *            the wrapped {@link ListBox}
	 */
	public OptionBox(final ListBox listBox) {
		if (listBox == null) {
			throw new IllegalArgumentException("listBox must not be null");
		}

		wrappedListBox = listBox;
	}

	/**
	 * Creates a new OptionBox from the specified serialized string.
	 * 
	 * @param serialized
	 *            serialized string, as created in serialize
	 * @return new OptionBox
	 */
	public static OptionBox deserialize(String serialized) {
		// TODO implement method
		return null;
	}

	@Override
	public void lock() {
		wrappedListBox.setEnabled(false);
	}

	@Override
	public void unlock() {
		wrappedListBox.setEnabled(true);
	}

	@Override
	public String read() {
		return wrappedListBox.getSelectedItemText();
	}

	@Override
	public String serialize() {
		// TODO Methode implementieren
		return null;
	}

	@Override
	public boolean isLocked() {
		return wrappedListBox.isEnabled();
	}

	@Override
	public void hide() {
		wrappedListBox.setVisible(false);
	}

	@Override
	public void show() {
		wrappedListBox.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return wrappedListBox.isVisible();
	}

	@Override
	public void setAction(Action action) {
		if (action == null) {
			throw new IllegalArgumentException("action must not be null");
		}

		if (currentEvent != null) {
			currentEvent.removeHandler();
		}
		currentEvent = wrappedListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				action.run();
			}
		});
	}
}
