package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * Button that slides open a panel when clicked.
 */
public class DisclosureButton implements Clickable, Hideable, Lockable {

	private DisclosurePanel wrappedPanel;

	/**
	 * 
	 * @param panel
	 *            Panel to adapt (this is the "Button" with the sliding
	 *            mechanism itself - not the panel within it)
	 */
	public DisclosureButton(DisclosurePanel panel) {
		/*
		 * Wir sollten die Bilder nicht im Konstruktor des Adapters übergeben,
		 * das sie nur sehr umständlich geändert werden können
		 */
		wrappedPanel = panel;
	}

	@Override
	public void lock() {
		// TODO im geschlossenen Zustand kann das Panel eh nicht verwendet
		// werden. Soll also die möglichkeit unterbunden werden, das Panel zu
		// öffnen oder Elemente im Panel zu verwenden?
	}

	@Override
	public void unlock() {
		// TODO siehe oben
	}

	@Override
	public boolean isLocked() {
		// TODO siehe oben
		return false;
	}

	@Override
	public void hide() {
		wrappedPanel.setOpen(false);
	}

	@Override
	public void show() {
		wrappedPanel.setOpen(true);
	}

	@Override
	public boolean isShown() {
		return wrappedPanel.isOpen();
	}

	@Override
	public void setAction(Action action) {
		/*
		 * TODO muss implementiert werden. Aber was soll setAction überhaupt
		 * bewirken?
		 */
	}
}
