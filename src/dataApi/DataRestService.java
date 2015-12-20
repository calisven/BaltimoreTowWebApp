package dataApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import greatSuccess.DatabaseSingleton;
import greatSuccess.HelperClass;


@Path("/")
public class DataRestService {

	// Tedious. This data is placed in the key portion of the key value pair 
	// JSON responses for easier parsing. The dates are also used to determine
	// the last day of a specific month, and for DB querying 
	private final String[] months2014 = {"01/01/2014", "02/01/2014","03/01/2014","04/01/2014","05/01/2014","06/01/2014",
										"07/01/2014","08/01/2014","09/01/2014","10/01/2014","11/01/2014","12/01/2014"};
	
	private final String[] months2015 = {"01/01/2015", "02/01/2015","03/01/2015","04/01/2015","05/01/2015","06/01/2015",
										"07/01/2015","08/01/2015","09/01/2015","10/01/2015","11/01/2015","12/01/2015"};
	
	private final String[] monthStrings = {"January", "February", "March", "April", "May", "June", "July", 
											"August", "September", "October", "November", "December"};
	

    @GET
    @Path("/heartbeat")
    /**
     * Simple test function for determining
     * if the API and database is available
     * @return
     */
    public Response getDefaultUserInJSON() {
        return Response.status(200).build();
    }
    
    
    @GET
    @Path("/monthlyTows")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Returns a significant amount of vehicle data, 
     * including vehicle tows total, tow charges, vehicle
     * stolen status, etc. These queries are time-intensive,
     * and though it would ideally be suited to separate these
     * into separate rest calls, this would significantly impact
     * client-side performance
     * @return
     */
    public Response getMonthlyTows() {
    	
    	DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
        DB db = dbSingleton.getDatabase();
        DBCollection coll = db.getCollection("cars");
        
        int totals2014 = 0;
        int totals2015 = 0;
        
        // Datasets that will be inserted into JSON responses and later parsed by the
        // client. The the key / value pairs are <MONTH, DATA>
        LinkedHashMap<String, Integer> year2014 = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> year2015 = new LinkedHashMap<String, Integer>();
        
        LinkedHashMap<String, Integer> year2014Paid = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> year2015Paid = new LinkedHashMap<String, Integer>();
        
        LinkedHashMap<String, Integer> year2014Stolen = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> year2015Stolen = new LinkedHashMap<String, Integer>();
        
        // Object used to provide what fields are (and aren't) returned for a successful query
        BasicDBObject fields = new BasicDBObject("vehicleMake",true).append("vehicleModel", true)
        		.append("totalPaid", true).append("stolenVehicleFlag", true)
        		.append("_id",false);
        
        // Iterate over every month, query data for that specific month
        for ( int i=0; i < months2014.length; i++ ) {
        	
        	BasicDBObject query = new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(months2014[i]))
        											.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(months2014[i]))));

        	// Will hold the result of a specific query
        	DBCursor cursor = coll.find(query, fields);

        	// Add to the total number of cars towed for that
        	// specific month
        	year2014.put(monthStrings[i], cursor.count());
        	
        	// Add to the total number of cars towed
        	totals2014 += cursor.count();
        	
        	int monthlyPaid = 0;
        	int numberStolen = 0;
        	
        	// Iterate over every returned field for
        	// data parsing
        	while(cursor.hasNext()) {
        		
        		DBObject nextEntry = cursor.next();
        		
        		// There were empty entries (many of them...)
        		String temp = (String) nextEntry.get("totalPaid");
        		
        		// Add to the total amount of money paid that month
        		// in tows
        		if (temp != "") {
        			temp = temp.replace("$", "");
        			monthlyPaid += Float.parseFloat(temp);
        		}
        		else {
        			// Vehicle was never claimed / paid for
        		}
        		// Check if this is a stolen vehicle. Entries in the DB are often
        		// blank, thus requiring a check if the value is castable to Integer
        		if ( nextEntry.get("stolenVehicleFlag") instanceof Integer ) {
        			
        			Integer wasStolen = (Integer)nextEntry.get("stolenVehicleFlag");
        			if ( wasStolen == 1 ) {
        				numberStolen ++;
        			}
        		}
        		
        	}
        	
        	// Add JSON data key / value pairs
        	// The monthly paid is an average of every vehicle towed
        	year2014Paid.put(monthStrings[i], monthlyPaid / cursor.count());
        	year2014Stolen.put(monthStrings[i], numberStolen);
        }
        
        // Repeat for year 2015. Ideally this would all be put in
        // another function
        for ( int i=0; i < months2015.length; i++ ) {
        	
        	BasicDBObject query = new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(months2015[i]))
        											.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(months2015[i]))));

        	DBCursor cursor = coll.find(query);
        	
        	year2015.put(monthStrings[i], cursor.count());
        	
        	totals2015 += cursor.count();
        	
        	int monthlyPaid = 0;
        	int numberStolen = 0;
	       	
        	while(cursor.hasNext()) {
        		
        		DBObject nextEntry = cursor.next();
        		// There were empty entries (many of them...)
        		String temp = (String) nextEntry.get("totalPaid");
        		if (temp != "") {
        			temp = temp.replace("$", "");
        			monthlyPaid += Float.parseFloat(temp);
        		}
        		else {
        			// Vehicle was never claimed
        		}
        		// Check if this is a stolen vehicle. Entries in the DB are often
        		// blank, thus requiring a check if the value is castable to Integer
        		if ( nextEntry.get("stolenVehicleFlag") instanceof Integer ) {
        			
        			Integer wasStolen = (Integer)nextEntry.get("stolenVehicleFlag");
        			if ( wasStolen == 1 ) {
        				numberStolen ++;
        			}
        		}
        		
        	}
        	
        	year2015Paid.put(monthStrings[i], monthlyPaid / cursor.count());
        	year2015Stolen.put(monthStrings[i], numberStolen);
        }
	        
	    // Will hold the return data
	    JSONObject merge = new JSONObject();
	    
	    // Combine data results into JSON format
	    try {
			merge.put("2014", year2014);
			merge.put("2015", year2015);
			merge.put("2014Total", totals2014);
			merge.put("2015Total", totals2015);
			merge.put("combinedTotal", totals2014 + totals2015);
			merge.put("2014Paid", year2014Paid);
			merge.put("2015Paid", year2015Paid);
			merge.put("2014Stolen", year2014Stolen);
			merge.put("2015Stolen", year2015Stolen);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
    	return Response.status(200).entity(merge).build();
    }
    
  
    @GET
    @Path("/vehicle/")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Allows the user to query individual vehicle make / model 
     * data
     * @param vehicleMake
     * @param vehicleModel
     * @return Total vehicles on a monthly basis that were towed
     */
    public Response getMainData(@QueryParam("vehicleMake") String vehicleMake, 
    		@QueryParam("vehicleModel") String vehicleModel) {
    	
    	// Unfortunately, all vehicles were capitalized in the database...
    	if ( vehicleMake != null && ! vehicleMake.isEmpty() ) {
    		vehicleMake = HelperClass.capitalize(vehicleMake);

    		// This is an optional parameter
    		if ( vehicleModel != null && ! vehicleModel.isEmpty()) {
    			vehicleModel = HelperClass.capitalize(vehicleModel);
    		}
    	}
    	// They did a bad thing.
    	else {
    		return Response.status(400).build();
    	}
    	
    	DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
        DB db = dbSingleton.getDatabase();
        DBCollection coll = db.getCollection("cars");
        
        JSONObject array = new JSONObject();
        List<Integer> array2015 = new ArrayList<Integer>();
        List<Integer> array2014 = new ArrayList<Integer>();
        
        BasicDBObject fields = new BasicDBObject("vehicleMake", true).append("vehicleModel", true).append("_id",false);
        
        for ( int i=0; i < months2015.length; i++ ) {
        	BasicDBObject query = new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(months2015[i]))
        											.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(months2015[i]))))
        											.append("vehicleMake", vehicleMake);
        	
        	// Add vehicleModel to the query
        	if ( vehicleModel != null && ! vehicleModel.isEmpty() ) {
        		query.append("vehicleModel", vehicleModel);
        	}
        	
        	DBCursor cursor = coll.find(query, fields);
        	
        		array2015.add(cursor.count());
        }
        
        for ( int i=0; i < months2014.length; i++ ) {
        	
        	BasicDBObject query = new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(months2014[i]))
        											.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(months2014[i]))))
        											.append("vehicleMake", vehicleMake);
        	
        	if ( vehicleModel != null && ! vehicleModel.isEmpty() ) {
        		query.append("vehicleModel", vehicleModel);
        	}
        	
        	DBCursor cursor = coll.find(query, fields);
        	
        	array2014.add(cursor.count());
        }

        try {
			array.put("2014", array2014);
			array.put("2015", array2015);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
    	return Response.status(200).entity(array).build();

    }
    
    
}
