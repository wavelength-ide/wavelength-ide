package edu.kit.wavelength.server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseServiceImplTest {

	private static File folder;
	private static File file;

	private DatabaseServiceImpl database;

	@BeforeClass
	public static void setFolder() {
		// create database folder required by databaseServiceImpl
		folder = new File("database");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// delete existing database to ensure the tests' running on an empty database
		file = new File("database/database.db");
		if (file.exists()) {
			file.delete();
		}
	}

	@AfterClass
	public static void deleteFolder() {
		if (file.exists()) {
			file.delete();
		}
		if (folder.exists()) {
			folder.delete();
		}
	}

	@Before
	public void setUp() {
		database = new DatabaseServiceImpl();
	}

	@After
	public void cleanUp() {
		file.delete();
	}

	// creation tests
	@Test
	public void folderCreationTest() {
		folder.delete();
		database = new DatabaseServiceImpl();
		assertEquals(true, folder.exists());
	}

	@Test
	public void fileCreationTest() {
		file.delete();
		database = new DatabaseServiceImpl();
		assertEquals(true, file.exists());
	}

	// getSerialization(final String id) tests
	@Test
	public void getSerializationWithNullArgument() {
		assertEquals(null, database.getSerialization(null));
	}

	@Test
	public void getSerializationWithNonExistentID() {
		assertEquals(null, database.getSerialization("non-existent id"));
	}

	@Test
	public void getSerializationTest() {
		String testString = "serialization test";
		String id = database.addEntry(testString);
		assertEquals(testString, database.getSerialization(id));
	}

	@Test
	public void getSerializationTwoTimes() {
		String testString = "double serialization";
		String id = database.addEntry(testString);
		assertEquals(database.getSerialization(id), database.getSerialization(id));
	}

	@Test
	public void getSerializationError() {
		String errorString = "org.sqlite.SQLiteException";
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setErr(new PrintStream(stream));
		file.delete();
		database.getSerialization("don't care");
		assertTrue(stream.toString().startsWith(errorString));
	}

	// addEntry(final String serialization) tests
	@Test
	public void addEntryTestWithNullArgument() {
		assertNotEquals(null, database.addEntry(null));
	}

	@Test
	public void addEntryTest() {
		assertNotEquals(null, database.addEntry("serialization String"));
	}

	@Test
	public void addEntryDuplicate() {
		String testString = "duplicate String";
		String id = database.addEntry(testString);
		assertEquals(id, database.addEntry(testString));
	}

	@Test
	public void addEntryError() {
		String errorString = "org.sqlite.SQLiteException";
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setErr(new PrintStream(stream));
		file.delete();
		database.addEntry("don't care");
		assertTrue(stream.toString().startsWith(errorString));
	}

	// mixed tests
	@Test
	public void nullTest() {
		String id = database.addEntry(null);
		assertEquals(null, database.getSerialization(id));
	}

	@Test
	public void injectionTest() {
		String injectedInsert = ";INSERT INTO map (id, serialization) VALUES (injectedID, injectedSerialization)";
		database.addEntry(injectedInsert);
		database.getSerialization(injectedInsert);

		assertEquals(null, database.getSerialization("injectedID"));
		assertNotEquals(null, database.addEntry("injectedSerialization"));
	}

	@Test
	public void shutDownTest() {
		String shutDown = ";GO EXEC cmdshell('shutdown /s')";
		database.addEntry(shutDown);
		database.getSerialization(shutDown);
	}

}
