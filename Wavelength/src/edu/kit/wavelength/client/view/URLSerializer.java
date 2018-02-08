package edu.kit.wavelength.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.kit.wavelength.client.database.DatabaseService;
import edu.kit.wavelength.client.database.DatabaseServiceAsync;

/**
 * Serializes the application's state into an URL every N milliseconds.
 */
public class URLSerializer {
	
	private List<SerializationObserver> serializationOutputs;
	private int pollingDelayMS;
	
	/**
	 * Creates a new serializer.
	 * @param serializationOutputs Observers to update with new serialized URL
	 * @param pollingDelayMS Delay between every serialization iteration. The serializable may change, so we poll it.
	 */
	public URLSerializer(List<SerializationObserver> serializationOutputs, int pollingDelayMS) {
		this.serializationOutputs = serializationOutputs;
		this.pollingDelayMS = pollingDelayMS;
	}
	
	/**
	 * Starts polling (serializing) the serializable.
	 */
	public void startPolling() {
		Scheduler.get().scheduleFixedDelay(this::serialize, pollingDelayMS);
	}
	
	/**
	 * Executes a serialization instantly.
	 * @return whether the serializer will continue to poll after this call
	 */
	public boolean serialize() {
		if(App.get().executor().isRunning()) {
			// should not serialize with a running execution
			return true;
		}
		// create database entry and url
		String serialization = App.get().serialize().toString();
		DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
		AsyncCallback<String> addEntryCallback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				App.get().outputArea().add(new Text(caught.getMessage()));
			}
			
			public void onSuccess(String id) {
				serializationOutputs.forEach(o -> o.updateSerialized(id));
			}
		};
		databaseService.addEntry(serialization, addEntryCallback);
		
		// return true to keep going
		return true;
	}
	
}
