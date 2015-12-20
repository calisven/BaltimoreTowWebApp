package greatSuccess;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
 
public class DatabaseSingleton {
 
 private static DatabaseSingleton mDbSingleton;
  
 private static MongoClient mongoClient;
     
 private static DB db ;
  
 private static final String dbHost = "localhost";
 private static final int dbPort = 27017;
 private static final String dbName = "test";
 
 private DatabaseSingleton(){};
  
 public static DatabaseSingleton getInstance(){
	 
  if( mDbSingleton == null ){
	  mDbSingleton = new DatabaseSingleton();
  }
  return mDbSingleton;
 } 
  
 public DB getDatabase() {
	 
	 if( mongoClient == null ){
		 try {
			 System.out.println("Opening mongo client instance");
			 mongoClient = new MongoClient(dbHost , dbPort);
			 
		 } catch (UnknownHostException e) {
			 System.out.println("Unknown host exception");
			 return null;
		 }
	 }
	 
	 if( db == null ) {
		 db = mongoClient.getDB(dbName);
	 }
	  
	 return db;
}
 
}