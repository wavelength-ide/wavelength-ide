package edu.kit.wavelength.server.serialization;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {
	/**
	 * Returns serialization belonging to given id if an entry exists,
	 *  else returns {@code null}
	 * @param id unique id
	 * @return serialization
	 */
	String getSerialization(final String id);
	/**
	 * Adds an entry for the given serialization if it is not already in the database.
	 * @param serialization valid serialization
	 */
	void addEntry(final String serialization);
}
