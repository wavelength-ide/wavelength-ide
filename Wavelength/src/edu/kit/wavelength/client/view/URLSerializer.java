package edu.kit.wavelength.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gwt.core.client.Scheduler;

import edu.kit.wavelength.client.model.serialization.Serializable;

public class URLSerializer {

	private Map<Object, String> entities = new HashMap<>();
	
	private Serializable serializable;
	private List<SerializationObserver> serializationOutputs;
	private int pollingDelayMS;
	
	public URLSerializer(Serializable serializable, List<SerializationObserver> serializationOutputs, int pollingDelayMS) {
		this.serializable = serializable;
		this.serializationOutputs = serializationOutputs;
		this.pollingDelayMS = pollingDelayMS;
	}
	
	public void startPolling() {
		Scheduler.get().scheduleFixedDelay(this::serialize, pollingDelayMS);
	}
	
	public boolean serialize() {
		// (convert serializable to URL)
		String url = null;
		serializationOutputs.forEach(o -> o.updateSerialized(url));
		// return true to keep going
		return true;
	}
	
}
