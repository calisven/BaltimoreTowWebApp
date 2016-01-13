package dataApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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


@Path("")
public class DataRestService {
	
	// To add a new year, simply, add the year string to 'yearsCovered
	// and another in 'allYears'
	private final String[] yearsCovered = {"2014", "2015"};
	
	private final String[][] allYears = {HelperClass.getMonthsArray(2014), HelperClass.getMonthsArray(2015)};
	
	private final String[] monthStrings = {"January", "February", "March", "April", "May", "June", "July", 
											"August", "September", "October", "November", "December"};
	
    @GET
    @Path("/heartbeat")
    /**
     * Simple test function for determining
     * if the API and database is available
     * @return
     */
    public Response getHeartbeat() {
    	
        return Response.status(200).build();
    }
    
    @GET
    @Path("/years")
    /**
     * Simple test function for determining
     * if the API and database is available
     * @return
     */
    @Produces(MediaType.TEXT_PLAIN)
    public String getAvailableYears() {
    	
        return Arrays.toString(yearsCovered);
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
        
        // Object used to provide what fields are (and aren't) returned for a successful query
        BasicDBObject fields = new BasicDBObject("vehicleMake",true).append("vehicleModel", true)
        		.append("totalPaid", true).append("stolenVehicleFlag", true)
        		.append("_id",false);
        
        YearResultObject[] results = new YearResultObject[allYears.length];
        
        for ( int i=0; i < yearsCovered.length; i++ ) {
        	results[i] = doStuff(allYears[i], yearsCovered[i], coll, fields);
        }
	        
	    // Will hold the return data
	    JSONObject merge = new JSONObject();
	    
	    int yearsTotals = 0;
	    
	    for( int i=0; i < yearsCovered.length; i++ ) {
	    
	    	// Combine data results into JSON format
	    	try {
				merge.put(results[i].getYearName(), results[i].getYearResults());
				merge.put(results[i].getYearName() + "Total", results[i].getTotalVehiclesTowed());
				yearsTotals += results[i].getTotalVehiclesTowed();
				merge.put(results[i].getYearName() + "Paid", results[i].getYearPaid());
				merge.put(results[i].getYearName() + "Stolen", results[i].getYearStolen());
			
	    	} catch (JSONException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    		return Response.status(500).build();
	    	}
	    }
	    
	    try {
			merge.put("combinedTotal", yearsTotals);
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
    	
    	HashMap<String,String> makeModel = DataRestHelper.validateVehicle(vehicleMake, vehicleModel);
    	
    	if (makeModel != null) {
    		vehicleMake = makeModel.get("vehicleMake");
    		vehicleModel = makeModel.get("vehicleModel");
    	}
    	else {
    		return Response.status(400).build();
    	}
    	
    	DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
        DB db = dbSingleton.getDatabase();
        DBCollection coll = db.getCollection("cars");
        
        JSONObject array = new JSONObject();
        
        BasicDBObject fields = new BasicDBObject("vehicleMake", true).append("vehicleModel", true)
        		.append("_id",false);
        
        for ( int i=0; i < allYears.length; i++ ) {
        	
        	List<Integer> yearArray = new ArrayList<Integer>();
        	
        	for( int monthValue=0; monthValue < allYears[i].length; monthValue++ ) {
        		
            	BasicDBObject query = getYearlyQueryWithVehicle(allYears[i][monthValue], vehicleMake);
            	
            	// Add vehicleModel to the query
            	if ( vehicleModel != null && ! vehicleModel.isEmpty() ) {
            		query.append("vehicleModel", vehicleModel);
            	}
            	
            	DBCursor cursor = coll.find(query, fields);
            	
            	yearArray.add(cursor.count());
        	}
        	
            try {
    			array.put(yearsCovered[i], yearArray);
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
        }
        
    	return Response.status(200).entity(array).build();

    }
    
    @GET
    @Path("/stolen/")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Allows the user to query individual vehicle make / model 
     * data
     * @param vehicleMake
     * @param vehicleModel
     * @return Total vehicles on a monthly basis that were towed
     */
    public Response getStolenData(@QueryParam("vehicleMake") String vehicleMake, 
    		@QueryParam("vehicleModel") String vehicleModel) {
    	
    	HashMap<String,String> makeModel = DataRestHelper.validateVehicle(vehicleMake, vehicleModel);
    	
    	if (makeModel != null) {
    		vehicleMake = makeModel.get("vehicleMake");
    		vehicleModel = makeModel.get("vehicleModel");
    	}
    	else {
    		return Response.status(400).build();
    	}
    	
    	DatabaseSingleton dbSingleton = DatabaseSingleton.getInstance();
        DB db = dbSingleton.getDatabase();
        DBCollection coll = db.getCollection("cars");
        
        JSONObject array = new JSONObject();
        
        BasicDBObject fields = new BasicDBObject("vehicleMake", true).append("stolenVehicleFlag", true)
        		.append("vehicleModel", true).append("_id",false);
        
        for ( int i=0; i < allYears.length; i++) {
        	
        	List<Integer> yearArray = new ArrayList<Integer>();
        	
        	for ( int monthValue=0; monthValue < allYears[i].length; monthValue++ ) {
        		
        		BasicDBObject query = getYearlyQueryWithVehicle(allYears[i][monthValue], vehicleMake);
            	
            	// Add vehicleModel to the query
            	if ( vehicleModel != null && ! vehicleModel.isEmpty() ) {
            		query.append("vehicleModel", vehicleModel);
            	}
            	
            	DBCursor cursor = coll.find(query, fields);
            	
            	int numberStolen = 0;
            	
            	// Iterate over every returned field for
            	// data parsing
            	while(cursor.hasNext()) {
            	
            		DBObject nextEntry = cursor.next();
            		// Check if this is a stolen vehicle. Entries in the DB are often
            		// blank, thus requiring a check if the value is castable to Integer
            		if ( nextEntry.get("stolenVehicleFlag") instanceof Integer ) {
        			
            			Integer wasStolen = (Integer)nextEntry.get("stolenVehicleFlag");
            			
            			if ( wasStolen == 1 ) {
            				numberStolen ++;
            			}
            		}
            	}
            	yearArray.add(numberStolen);     		
        	}
        	
        	try {
    			array.put(yearsCovered[i], yearArray);
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
        }
        
    	return Response.status(200).entity(array).build();

    }
    
    /**
     * Returns a DB query used to get all tows for the given
     * input month
     * @param month
     * @return
     */
    public BasicDBObject getYearlyQuery(String month) {
    	
    	return new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(month))
				.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(month))));
    }
    
    /**
     * Returns a DB query used to get all tows for the given input
     * month and vehicle
     * @param month
     * @param vehicleMake
     * @return
     */
    public BasicDBObject getYearlyQueryWithVehicle(String month, String vehicleMake) {
    	
    	return new BasicDBObject("towedDateTime", new BasicDBObject("$gte", new Date(month))
				.append("$lte", HelperClass.getLastDateOfCurrentMonth(new Date(month))))
				.append("vehicleMake", vehicleMake);
    }
    
    public YearResultObject doStuff(String[] monthList, String year, DBCollection coll, BasicDBObject fields) {
    	
    	YearResultObject results = new YearResultObject(year);
    	int totalVehiclesTowed = 0;
    	
        // Iterate over every month, query data for that specific month
        for ( int i=0; i < monthList.length; i++ ) {
        	
        	BasicDBObject query = getYearlyQuery(monthList[i]);

        	// Will hold the result of a specific query
        	DBCursor cursor = coll.find(query, fields);

        	// Add to the total number of cars towed for that
        	// specific month
        	results.setYearResults(monthStrings[i], cursor.count());
        	
        	// Add to the total number of cars towed
        	totalVehiclesTowed += cursor.count();
        	
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
        	results.setYearPaid(monthStrings[i], monthlyPaid / cursor.count());
        	results.setYearStolen(monthStrings[i], numberStolen);
        }
        
        results.setTotalVehiclesTowed(totalVehiclesTowed);
        
        return results;
    }
}
