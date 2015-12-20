package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.DB;

import greatSuccess.DatabaseSingleton;

public class DbConnectionTest {

	@Test
	public void testDbConnection() {
		
		DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
		
		// Singleton should never return null
		assertNotEquals(dbSingleton,null);
		
		DB db = dbSingleton.getDatabase();
		
		// A failure to connect results in a 
		// null return value
		assertNotEquals(db, null);

	}
	
	@Test
	public void testDbTable() {
		
		DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
		DB db = dbSingleton.getDatabase();
		
		// Checks for database collection existance
		assertTrue(db.collectionExists("cars"));
	}

}
