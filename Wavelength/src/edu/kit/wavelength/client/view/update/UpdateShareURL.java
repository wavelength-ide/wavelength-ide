package edu.kit.wavelength.client.view.update;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.SerializationObserver;

/**
 * Observer that updates the URL in the share panel.
 */
public class UpdateShareURL implements SerializationObserver {

	@Override
	public void updateSerialized(String id) {
		App.get().sharePanel().setText(Window.Location.getHost() + Window.Location.getPath() + "#" + History.encodeHistoryToken(id));
	}

}
