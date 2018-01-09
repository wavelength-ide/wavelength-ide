package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.SerializationObserver;

public class UpdateShareURL implements SerializationObserver {

	@Override
	public void updateSerialized(String s) {
		App.get().sharePanel().write(s);
	}

}
