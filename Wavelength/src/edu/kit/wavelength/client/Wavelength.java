package edu.kit.wavelength.client;

import com.google.gwt.core.client.EntryPoint;
import edu.kit.wavelength.client.view.App;

/**
 * This class marks the entry point of the application.
 */
public class Wavelength implements EntryPoint {

	/**
	 * This method is called when the application is first started. It initializes
	 * the application's {@link App} class.
	 */
	@Override
	public void onModuleLoad() {
		// initializes the app
		App.get();
	}
}
