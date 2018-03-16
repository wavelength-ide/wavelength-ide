package edu.kit.wavelength.client.view.update;

import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.SerializationObserver;

/**
 * Observer that updates the URL in the share panel.
 */
public class UpdateShareURL implements SerializationObserver {
	
	@Override
	public void updateSerialized(String id) {
		UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
		urlBuilder.setParameter(App.KEY, id);
		App.get().sharePanel().setText(urlBuilder.buildString());
	}

}
