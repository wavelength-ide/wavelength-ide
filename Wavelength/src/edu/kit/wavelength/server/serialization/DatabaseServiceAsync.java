package edu.kit.wavelength.server.serialization;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseServiceAsync {
	void getSerialization(final String id, AsyncCallback<String> callback);
	void addEntry(final String serialization, AsyncCallback<Void> callback);
}
