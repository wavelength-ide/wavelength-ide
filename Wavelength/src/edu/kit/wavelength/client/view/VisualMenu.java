package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;

import edu.kit.wavelength.client.action.Action;

/**
 * A VisualMenu is an adapter class for the GWT MenuBar.
 * 
 * It is represented by an Image. By clicking on the Image the Menu opens and
 * presents its options. In addition a the Menu can be blocked. This means that
 * the menu can still be opened but clicking an an option has no effect.
 */
public class VisualMenu implements Blockable, EntryInjectable {

	/**
	 * Constructs a new and empty VisualMenu.
	 * 
	 * @param image
	 *            The Image that represents this Menu.
	 * @param menu
	 *            The GWT MenuBar that this class wraps.
	 */
	public VisualMenu(Image image, MenuBar menu) {

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

	}

	@Override
	public void addEntry(String name, Action action) {
		// TODO Auto-generated method stub

	}
}
