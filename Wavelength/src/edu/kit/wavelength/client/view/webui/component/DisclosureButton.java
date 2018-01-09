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

	/**
	 * 
	 * @param panel Panel to adapt (this is the "Button" with the sliding mechanism itself - not the panel within it)
	 * @param imageWhenLocked Image to display when button is locked
	 * @param imageWhenUnlocked Image to display when button is unlocked
	 */
	public DisclosureButton(DisclosurePanel panel, Image imageWhenLocked, Image imageWhenUnlocked) {
		
	}

	@Override
	public void lock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isShown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAction(Action action) {
		// TODO Auto-generated method stub
		
	}
	
}
