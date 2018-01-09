package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.SerializationObserver;

/**
 * Observer that updates the URL in the share panel.
 */
public class UpdateShareURL implements SerializationObserver {

	@Override
	public void updateSerialized(String s) {
		App.get().sharePanel().write(s);
	}

}
