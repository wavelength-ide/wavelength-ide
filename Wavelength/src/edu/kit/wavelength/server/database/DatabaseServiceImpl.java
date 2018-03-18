package edu.kit.wavelength.server.database;

import java.io.File;
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
 * serializations. Note that this class uses the try-with-resources statement to
 * close resources upon finishing.
 */
public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService {

	private static final long serialVersionUID = 1L;
	public static final String NULL_CONNECTION = "Connection to database could not be established!";
	public static final String SQL_EXCEPTION_MESSAGE = "An SQL Exception occured!";

	// SQL-Commands
	private static final String databasePath = "jdbc:sqlite:database/database.db";
	private static final String createDatabase = 
			"CREATE TABLE IF NOT EXISTS map (\n id text PRIMARY KEY, \n serialization text);";
	private static final String selectSerialization = "SELECT serialization FROM map WHERE id = ?";
	private static final String selectID = "SELECT id FROM map WHERE serialization = ?";
	private static final String insertEntry = "INSERT INTO map (id, serialization) VALUES (?,?)";

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
		File file = new File("database");
		if (!file.exists()) {
			file.mkdir();
		}
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			try (Statement statement = connection.createStatement()) {
				statement.execute(createDatabase);
			}
		} catch (SQLException exception) {
			System.err.println(NULL_CONNECTION + exception.toString());
		}
	}

	@Override
	public String getSerialization(final String id) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(selectSerialization);) {
					statement.setString(1, id);
					try (ResultSet resultSet = statement.executeQuery()) {
						// note that resultSet is supposed to contain only one element
						if (resultSet.next()) {
							return resultSet.getString(1);
						} else {
							return null;
						}
					} catch (SQLException exception) {
						exception.printStackTrace();
						return null;
					}
				} catch (SQLException exception) {
					exception.printStackTrace();
					return null;
				}
			} else {
				System.err.println(NULL_CONNECTION);
				return null;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns the id assigned to a given serialization if it exists. Else returns
	 * null.
	 * 
	 * @param serialization
	 *            a serialization String
	 * @return id mapped to given serialization
	 */
	private String getID(final String serialization) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				try (PreparedStatement statement = connection.prepareStatement(selectID);) {
					statement.setString(1, serialization);
					try (ResultSet resultSet = statement.executeQuery()) {
						// note that resultSet is supposed to contain only one element
						if (resultSet.next()) {
							return resultSet.getString(1);
						} else {
							return null;
						}
					} catch (SQLException exception) {
						exception.printStackTrace();
						return null;
					}
				} catch (SQLException exception) {
					exception.printStackTrace();
					return null;
				}
			} else {
				System.err.println(NULL_CONNECTION);
				return null;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Override
	public String addEntry(final String serialization) {
		try (Connection connection = DriverManager.getConnection(databasePath)) {
			if (connection != null) {
				String assignedID = this.getID(serialization);
				if (assignedID == null) {
					// it could be possible that the generated id is already used although this is
					// quite unlikely
					String id = UUID.randomUUID().toString();
					try (PreparedStatement statement = connection.prepareStatement(insertEntry)) {
						statement.setString(1, id);
						statement.setString(2, serialization);
						statement.executeUpdate();
						return id.toString();

					} catch (SQLException exception) {
						System.err.println(exception.getMessage());
						return null;
					}
				} else {
					// return id if it already exists
					return assignedID;
				}
			} else {
				System.err.println(NULL_CONNECTION);
				return null;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}

	}

}
