package edu.kit.wavelength.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.kit.wavelength.client.database.DatabaseService;

/**
 * Implementation of {@link DatabaseService} running on server.
 * 
 * This implementation uses {@link UUID} objects as identifiers for
 * serializations.
 */
public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService {

	private static final long serialVersionUID = 1L;

	// SQL-Commands
	private static final String databasePath = "jdbc:sqlite:database/database.db";
	private static final String createDatabase = "CREATE TABLE IF NOT EXISTS map (\n id text PRIMARY KEY, \n serialization text);";
	private static final String selectSerialization = "SELECT serialization FROM map WHERE id = ?";
	private static final String selectID = "SELECT id FROM map WHERE serialization = ?";
	private static final String insertEntry = "INSERT INTO map(id, serialization) VALUES (?,?)";

	/**
	 * Initialize connection to database located at url given by
	 * {@value #databasePath}.
	 */
	public DatabaseServiceImpl() {
		this.initializeDatabase();
	}

	/**
	 * Initialize the database and create a table (if it does not already exist)
	 * mapping ids(as Strings) to serialization Strings.
	 */
	private void initializeDatabase() {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			try (Statement statement = connection.createStatement()) {
				statement.execute(createDatabase);
			}
		} catch (SQLException exception) {
			// do nothing because of autoclose
		}
	}

	@Override
	public String getSerialization(final String id) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(selectSerialization);) {
					statement.setString(1, id);
					try (ResultSet resultSet = statement.executeQuery()) {
						resultSet.next();
						return resultSet.getString(1);
					} catch (SQLException exception) {
						return null;
					}
				} catch (SQLException exception) {
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException exception) {
			return null;
		}

	}

	/**
	 * Returns the id for a given serialization if it exists.
	 * 
	 * @param serialization
	 *            a serialization String
	 * @return id mapped to given serialization
	 */
	private String getID(final String serialization) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(selectID);){
					statement.setString(1, serialization);
					try (ResultSet resultSet = statement.executeQuery()) {
						if (resultSet != null) {
							return resultSet.getString(1);
						} else {
							return null;
						}
					} catch (SQLException exception) {
						return null;
					}
				} catch (SQLException exception) {
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException exception) {
			return null;
		}
	}

	@Override
	public String addEntry(final String serialization) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				String assignedID = this.getID(serialization);
				if (assignedID == null) {
					// hopefully this is not already used
					String id = UUID.randomUUID().toString();
					try (PreparedStatement statement = connection.prepareStatement(insertEntry)) {
						statement.setString(1, id);
						statement.setString(2, serialization);
						statement.executeUpdate();
						return id.toString();
						
					} catch (SQLException exception) {
						return null;
					}
				} else {
					// return id if it already exists
					return assignedID;
				}
			} else {
				return null;
			}
		} catch (SQLException exception) {
			return null;
		}

	}

}
