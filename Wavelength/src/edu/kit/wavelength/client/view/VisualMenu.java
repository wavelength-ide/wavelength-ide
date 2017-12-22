package edu.kit.wavelength.client.view;

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
public class VisualMenu implements Blockable, EntryInjectable {

	public VisualMenu(Image image, MenuBar menu){

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

	}
<<<<<<< a56d2116b2653f4828c357071fa967f13f2b6fd6

	@Override
	public void addEntry(String name, Action action) {
		// TODO Auto-generated method stub
		
	}

=======
>>>>>>> Hauptsächlich änderungen an der OptionBox. Sie wird mit GWT funktionieren.
}
