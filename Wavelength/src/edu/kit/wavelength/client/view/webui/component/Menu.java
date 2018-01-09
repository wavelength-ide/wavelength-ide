package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This class wraps GWT's {@link DisclosurePanel}.
 * 
 * A PopUpPanel is represented by an icon that pops up a menu on click.
 * The icon depends on whether the Panel is deactivated or not according to the {@link Lockable} interface.
 */
public class Menu implements Lockable {

	/**
	 * Creates a new PopUpPanel.
	 * @param panel the wrapped {@link Menu}
	 * @param imageWhenUnblocked icon shown when this is not blocked
	 * @param imageWhenBlocked icon shown when this is blocked
	 */
	public Menu(final DisclosurePanel panel, final Image imageWhenUnblocked, final Image imageWhenBlocked) {
		
	}
	@Override
	public void lock() {
		
	}

	@Override
	public void unlock() {
		
	}

}
