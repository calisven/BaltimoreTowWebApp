package dataApi;

import java.util.LinkedHashMap;

public class YearResultObject {

	private LinkedHashMap<String, Integer> yearResults;
	private LinkedHashMap<String, Integer> yearPaid;
	private LinkedHashMap<String, Integer> yearStolen;
	
	private int totalVehiclesTowed;
	
	private String year;
	
	public YearResultObject(String year) {
		
		this.year = year;
		
		 yearResults = new LinkedHashMap<String, Integer>();
		 yearPaid = new LinkedHashMap<String, Integer>();
		 yearStolen = new LinkedHashMap<String, Integer>();
	}
	

	public LinkedHashMap<String, Integer> getYearResults() {
		return yearResults;
	}

	public void setYearResults(String month, Integer count) {
    	// Add to the total number of cars towed for that
    	// specific month
    	this.yearResults.put(month, count);
	}

	public LinkedHashMap<String, Integer> getYearPaid() {
		return yearPaid;
	}

	public void setYearPaid(String month, Integer count) {
		this.yearPaid.put(month, count);
	}

	public LinkedHashMap<String, Integer> getYearStolen() {
		return yearStolen;
	}

	public void setYearStolen(String month, Integer count) {
		this.yearStolen.put(month, count);
	}

	public String getYearName() {
		return year;
	}

	public void setYearName(String year) {
		this.year = year;
	}


	public Integer getTotalVehiclesTowed() {
		return totalVehiclesTowed;
	}


	public void setTotalVehiclesTowed(Integer totalVehiclesTowed) {
		this.totalVehiclesTowed = totalVehiclesTowed;
	}
	
	
}
