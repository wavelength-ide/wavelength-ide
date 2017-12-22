package edu.kit.wavelength.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;

import edu.kit.wavelength.client.action.Action;

/**
 * A VisualMenu is an adapter class for a GWT MenuBar.
 * 
 * It is represented by an Image. By clicking on the Image the Menu opens and
 * presents its options. In addition a the Menu can be blocked. This means that
 * the menu can still be opened but clicking an an option has no effect.
 */
public class VisualMenu implements Blockable {

	public VisualMenu(Image image, List<String> labels, List<Action> actions, MenuBar menu) {

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

	}

}
