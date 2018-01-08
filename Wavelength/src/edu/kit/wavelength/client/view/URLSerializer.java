package edu.kit.wavelength.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Writable;

public class URLSerializer {

	private Map<Object, String> entities = new HashMap<>();
	
	private List<Serializable> serializables;
	private List<Writable> serializationOutputs;
	private int pollingDelayMS;
	
	public URLSerializer(List<Serializable> serializables, List<Writable> serializationOutputs, int pollingDelayMS) {
		this.serializables = serializables;
		this.serializationOutputs = serializationOutputs;
		this.pollingDelayMS = pollingDelayMS;
	}
	
	public void startPolling() {
		Scheduler.get().scheduleFixedDelay(this::serialize, pollingDelayMS);
	}
	
	public boolean serialize() {
		// (serialize URL by querrying all serializables)
		// return true to keep going
		return true;
	}
	
}
