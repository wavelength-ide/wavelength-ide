package edu.kit.wavelength.client.view;

import java.util.List;

import com.google.gwt.core.client.Scheduler;

import edu.kit.wavelength.client.model.serialization.Serializable;

/**
 * Serializes a serializable into an URL every N milliseconds.
 */
public class URLSerializer {
	
	private Serializable serializable;
	private List<SerializationObserver> serializationOutputs;
	private int pollingDelayMS;
	
	/**
	 * Creates a new serializer.
	 * @param serializable Serializable to serialize
	 * @param serializationOutputs Observers to update with new serialized URL
	 * @param pollingDelayMS Delay between every serialization iteration. The serializable may change, so we poll it.
	 */
	public URLSerializer(Serializable serializable, List<SerializationObserver> serializationOutputs, int pollingDelayMS) {
		this.serializable = serializable;
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
		// (convert serializable to URL)
		String url = null;
		serializationOutputs.forEach(o -> o.updateSerialized(url));
		// return true to keep going
		return true;
	}
	
}
