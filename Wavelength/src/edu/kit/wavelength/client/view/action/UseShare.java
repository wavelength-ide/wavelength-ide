package edu.kit.wavelength.client.view.action;

import java.util.List;

import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.kit.wavelength.client.database.DatabaseService;
import edu.kit.wavelength.client.database.DatabaseServiceAsync;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.SerializationObserver;

/**
 * This action generates the permalink and toggles the dedicated panel. The
 * permalink encodes the current input, output and settings.
 */

public class UseShare implements Action {

	private static App app = App.get();
	private List<SerializationObserver> serializationOutputs;

	/**
	 * Creates a new UseShare action with a given list of
	 * {@link SerializationObserver} that are updated each time the action runs.
	 * 
	 * @param serializationOutputs
	 *            observers that are updated with the id of the database entry for
	 *            the serialization String
	 */
	public UseShare(List<SerializationObserver> serializationOutputs) {
		this.serializationOutputs = serializationOutputs;
	}

	@Override
	public void run() {
		if (app.executor().isRunning()) {
			app.executor().pause();
			Control.updateControls();
		}
		TextBox sharePanel = app.sharePanel();
		if (sharePanel.isVisible()) {
			sharePanel.setVisible(false);
		} else {
			// create database entry and url
			String serialization = App.get().serialize().toString();
			DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
			AsyncCallback<String> addEntryCallback = new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					System.err.println(caught.getMessage());
				}

				public void onSuccess(String id) {
					serializationOutputs.forEach(o -> o.updateSerialized(id));
				}
			};
			databaseService.addEntry(serialization, addEntryCallback);
			sharePanel.setVisible(true);
		}

	}

}
