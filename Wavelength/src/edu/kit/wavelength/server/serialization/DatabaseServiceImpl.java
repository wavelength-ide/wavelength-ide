package edu.kit.wavelength.server.serialization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String databasePath = "jdbc:sqlite:/lib/database.db";
	private static final String createDatabase = "CREATE TABLE IF NOT EXISTS map (\n id text PRIMARY KEY, \n serialization text);";
	private static final String selectSerialization = "SELECT serialization FROM map WHERE id = ?";
	private static final String selectID = "SELECT id FROM map WHERE serialization = ?";
	private static final String insertEntry = "INSERT INTO map(id, serialization) VALUES (?,?)";
	private Connection connection;

	public DatabaseServiceImpl() {
		this.connection = null;
		this.initializeDatabase();
	}

	private void initializeDatabase() {
		try {
			connection = DriverManager.getConnection(databasePath);
		} catch (SQLException exception) {
			throw new IllegalArgumentException("ERROR!!!");
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute(createDatabase);
		} catch (SQLException exception) {
			throw new IllegalArgumentException("ERROR!!!");
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
				throw new IllegalArgumentException("Error!!");
			}
		} else {
			return null;
		}
	}
	
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
				throw new IllegalArgumentException("Error!!");
			}
		} else {
			return null;
		}
	}

	@Override
	public void addEntry(final String serialization) {
		if (connection != null) {
			if (this.getID(serialization) == null) {
				//hopefully this is not already used
				String id = UUID.randomUUID().toString();
				try {
					PreparedStatement statement = connection.prepareStatement(insertEntry);
					statement.setString(1, id);
					statement.setString(2, serialization);
					statement.executeUpdate();
				} catch (SQLException exception) {
					throw new IllegalArgumentException("Error!!");
				}
			} 
			//do nothing if entry already exists
		} else {
			throw new IllegalArgumentException("Database not initialized!");
		}
	}

}
