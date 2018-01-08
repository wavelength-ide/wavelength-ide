package edu.kit.wavelength.client.view.webui.components;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Toggleable;

public class Checkbox implements Readable, Toggleable, Serializable {

	public Checkbox(String name) {
		
	}
	
	@Override
	public boolean isSet() {
		return false;
	}

	@Override
	public String read() {
		return null;
	}

}
