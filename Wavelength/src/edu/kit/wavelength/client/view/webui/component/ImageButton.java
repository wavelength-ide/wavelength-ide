package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * An ImageButton is an adapter class that wraps GWT's {@link Button} class.
 *
 * It is represented by an {@link Image}. The representing image depends on
 * whether the Button is deactivated or not. In addition it can be hidden from
 * the UI.
 */
public class ImageButton implements Lockable, Hideable, Clickable {
	/*
	 * TODO ImageButton sollte wirklich einen CustomButton (ACHTUNG CustomButton
	 * is abstract) adapten. Der kann auch viel leichter Bilder entgegennehmen
	 */

	private CustomButton wrappedButton;
	private HandlerRegistration currentEvent;

	/**
	 * Creates a new VisualButton.
	 *
	 * @param Custombutton
	 *            the wrapped {@link CustomButton}
	 * @param imageWhenActivated
	 *            The image that is shown when the Button is active
	 * @param imageWhenDeactivated
	 *            The image that is shown when the Button is deactivated
	 * 
	 */
	public ImageButton(final CustomButton button, final Image imageWhenActivated, final Image imageWhenDeactivated) {
		wrappedButton = button;
		/*
		 * TODO Bilder sollten über den Konstruktor in der AppKlasse übergeben
		 * werden. Ich habe nur umständliche wege gefunden das Bild des Knopfes
		 * nach seiner erstellung zu ändern.
		 */
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
	public void setAction(Action action) {
		if (currentEvent != null) {
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
	public boolean isShown() {
		return wrappedButton.isVisible();
	}

	@Override
	public boolean isLocked() {
		return wrappedButton.isEnabled();
	}
}
