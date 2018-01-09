package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.Panel;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This class wraps GWT's {@link Panel} and represents a menu.
 */
public class Menu implements Hideable {

	/**
	 * Creates a new Menu.
	 * @param panel the wrapped {@link Panel}
	 */
	public Menu(final Panel panel) {
		
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

}
