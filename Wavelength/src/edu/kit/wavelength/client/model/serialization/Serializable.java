package edu.kit.wavelength.client.model.serialization;

public interface Serializable {

	String serialize();
	void deserialize(String serialized);
	
}
