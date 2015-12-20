package test;

import static org.junit.Assert.*;

import org.junit.Test;

import greatSuccess.DatabaseSingleton;

public class DbSingletonTest {

	@Test
	public void testDbInstance() {
		
		DatabaseSingleton db = DatabaseSingleton.getInstance();
		
		// Singleton should never return null
		//assertNotEquals(db,null);
		
		DatabaseSingleton db2 = DatabaseSingleton.getInstance();
		
		// Singleton instances should always be the same instance
		assertEquals(db, db2);
	}

}
