package edu.kit.wavelength.client.view.webui.components;

import com.google.gwt.user.client.ui.CheckBox;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Toggleable;

/**
 * This class wraps GWT's {@link CheckBox}.
 */
public class Checkbox implements Readable, Toggleable, Serializable {

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
		// TODO Auto-generated method stub
		return null;
	}

}
