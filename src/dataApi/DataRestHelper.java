package dataApi;

import java.util.HashMap;

import greatSuccess.HelperClass;

public class DataRestHelper {

	public static HashMap<String, String> validateVehicle(String vehicleMake, String vehicleModel) {
		
		HashMap<String,String> returnPair = new HashMap<String,String>();
		
		// Unfortunately, all vehicles were capitalized in the database...
		if ( vehicleMake != null && ! vehicleMake.isEmpty() ) {
			vehicleMake = HelperClass.capitalize(vehicleMake);
			
			// This is an optional parameter
			if ( vehicleModel != null && ! vehicleModel.isEmpty()) {
				vehicleModel = HelperClass.capitalize(vehicleModel);
			}
			
			returnPair.put("vehicleMake", vehicleMake);
			returnPair.put("vehicleModel", vehicleModel);
			
			return returnPair;
		}
		// They did a bad thing.
		else {
			return null;
		}
	
	}
}
