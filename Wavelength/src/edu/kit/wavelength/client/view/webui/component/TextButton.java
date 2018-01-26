package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * A LabeldButton is an adapter class that wraps a GWT {@link Button}.
 *
 * It is labeled by text and can be locked and unlocked to prevent the User
 * from interacting with it. In Addition its behavior can be changed. This means
 * that the name of the label and the action that is performed when clicking
 * this button can be changed.
 */
public class TextButton implements Lockable, Hideable, Writable, Readable, Clickable {

	private Button wrappedButton;
	private HandlerRegistration currentEvent;
	
	/**
	 * Creates a new LabeledButton.
	 *
	 * @param button
	 *            the wrapped {@link Button}
	 * @param text
	 *            The label and name of this Button
	 * 
	 */
	public TextButton(final Button button, final String text) {
		if (button == null){
			throw new IllegalArgumentException("button must not be null");
		}
		
		wrappedButton = button;
		wrappedButton.setText(text);
		currentEvent = null;
	}

	@Override
	public void lock() {
		wrappedButton.setEnabled(false);
	}

	@Override
	public void unlock() {
		wrappedButton.setEnabled(true);
	}

	@Override
	public void hide() {
		wrappedButton.setVisible(false);
	}

	@Override
	public void show() {
		wrappedButton.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return wrappedButton.isVisible();
	}

	@Override
	public void setAction(Action action) {
		if (action == null){
			throw new IllegalArgumentException("action must not be null");
		}
		
		if(currentEvent != null){
			currentEvent.removeHandler();
		}
		currentEvent = wrappedButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				action.run();
			}
		});
	}

	@Override
	public boolean isLocked() {
		return wrappedButton.isEnabled();
	}

	@Override
	public void write(String input) {
		wrappedButton.setText(input);		
	}

	@Override
	public String read() {
		return wrappedButton.getText();
	}
}
