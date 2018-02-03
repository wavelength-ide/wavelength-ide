package edu.kit.wavelength.server.serialization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.kit.wavelength.client.serialization.DatabaseService;

/**
 * Implementation of {@link DatabaseService} running on server.
 * 
 * This implementation uses {@link UUID} objects as identifiers for serializations.
 */
public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService {

	private static final long serialVersionUID = 1L;
	
	//SQL-Commands
	private static final String databasePath = "jdbc:sqlite:database/database.db";
	private static final String createDatabase = "CREATE TABLE IF NOT EXISTS map (\n id text PRIMARY KEY, \n serialization text);";
	private static final String selectSerialization = "SELECT serialization FROM map WHERE id = ?";
	private static final String selectID = "SELECT id FROM map WHERE serialization = ?";
	private static final String insertEntry = "INSERT INTO map(id, serialization) VALUES (?,?)";
	
	private Connection connection;

	/**
	 * Initialize connection to database located at url given by {@value #databasePath}.
	 */
	public DatabaseServiceImpl() {
		this.connection = null;
		this.initializeDatabase();
	}

	/**
	 * Initialize the database and create a table (if it does not already exist) mapping ids(as Strings) to serialization Strings.
	 */
	private void initializeDatabase() {
		try {
			connection = DriverManager.getConnection(databasePath);
		} catch (SQLException exception) {
			//do nothing
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute(createDatabase);
		} catch (SQLException exception) {
			//do nothing
		}
	}

	@Override
	public String getSerialization(final String id) {
		if (connection != null) {
			try {
				PreparedStatement statement = connection.prepareStatement(selectSerialization);
				statement.setString(1, id);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet != null) {
					return resultSet.getString(1);
				} else {
					return null;
				}
			} catch (SQLException exception) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the id for a given serialization if it exists.
	 * @param serialization a serialization String
	 * @return id mapped to given serialization
	 */
	private String getID(final String serialization) {
		if (connection != null) {
			try {
				PreparedStatement statement = connection.prepareStatement(selectID);
				statement.setString(1, serialization);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet != null) {
					return resultSet.getString(1);
				} else {
					return null;
				}
			} catch (SQLException exception) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String addEntry(final String serialization) {
		if (connection != null) {
			String assignedID = this.getID(serialization);
			if (assignedID == null) {
				//hopefully this is not already used
				String id = UUID.randomUUID().toString();
				try {
					PreparedStatement statement = connection.prepareStatement(insertEntry);
					statement.setString(1, id);
					statement.setString(2, serialization);
					statement.executeUpdate();
					return id.toString();
				} catch (SQLException exception) {
					return null;
				}
			} else {
				//return id if it already exists
				return assignedID;
			}
		} else {
			return null;
		}
	}

}
