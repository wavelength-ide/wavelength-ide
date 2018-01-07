package edu.kit.wavelength.client.view.update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.wavelength.client.view.api.Writable;

public class URLSerializer implements Serializer {

	private Map<Object, String> entities = new HashMap<>();
	
	private List<Writable> serializationOutputs;
	
	public URLSerializer(Writable... serializationOutputs) {
		this.serializationOutputs = Arrays.asList(serializationOutputs);
	}
	
	@Override
	public String serialize() {
		// (serialize URL)
		return null;
	}
	
	@Override
	public void updateEntity(Object sourceEntity, String serializedState) {
		entities.put(sourceEntity, serializedState);
		String url = serialize();
		serializationOutputs.forEach(output -> output.write(url));
	}
	
}
