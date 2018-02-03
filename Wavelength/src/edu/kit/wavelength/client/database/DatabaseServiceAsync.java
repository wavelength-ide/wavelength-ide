package edu.kit.wavelength.client.database;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface for asynchronous calls to the database according to specifications in {@link DatabaseService}.
 */
public interface DatabaseServiceAsync {
	
	/**
	 * Asynchronous call to method specified in {@link DatabaseService#getSerialization(String)}
	 * 
	 * @see DatabaseService
	 * @param id serialization's id
	 * @param callback callback handler
	 */
	void getSerialization(final String id, AsyncCallback<String> callback);
	
	/**
	 * Asynchronous call to method specified in {@link DatabaseService#addEntry(String)}
	 * 
	 * @see DatabaseService
	 * @param serialization a valid serialization
	 * @param callback callback handler
	 */
	void addEntry(final String serialization, AsyncCallback<String> callback);
}
