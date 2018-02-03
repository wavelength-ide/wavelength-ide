package edu.kit.wavelength.client.view.update;

import com.google.gwt.user.client.History;

import edu.kit.wavelength.client.view.SerializationObserver;

/**
 * Observer that updates the browser URL.
 */
public class UpdateURL implements SerializationObserver {

	@Override
	public void updateSerialized(String id) {
		History.newItem(id);
	}

}
