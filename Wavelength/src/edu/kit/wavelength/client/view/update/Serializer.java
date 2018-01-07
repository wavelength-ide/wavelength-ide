package edu.kit.wavelength.client.view.update;

public interface Serializer {
	String serialize();
	void updateEntity(Object sourceEntity, String serializedState);
}
