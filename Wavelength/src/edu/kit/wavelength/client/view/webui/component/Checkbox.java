package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.CheckBox;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Toggleable;

/**
 * This class wraps GWT's {@link CheckBox}.
 */
public class Checkbox implements Readable, Hideable, Toggleable, Serializable, Lockable {

	/**
	 * Creates a new Checkbox
	 * 
	 * @param checkBox the wrapped {@link CheckBox}
	 * @param name the text shown next to the checkbox
	 */
	public Checkbox(final CheckBox checkBox, final String name) {
		
	}
	
	public static Checkbox deserialize(String serialized) {
		return null;
	}
	
	@Override
	public boolean isSet() {
		return false;
	}

	@Override
	public String read() {
		return null;
	}

	@Override
	public String serialize() {
		return null;
	}

	@Override
	public void lock() {

	}

	@Override
	public void unlock() {

	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void set() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unset() {
		// TODO Auto-generated method stub
		
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

}
