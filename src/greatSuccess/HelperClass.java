package greatSuccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A couple helper functions used in the Rest Service.
 * @author srivera
 *
 */
public class HelperClass {

	/**
	 * Receives an input date and returns the last day of
	 * the input month in a Date format.
	 * @param date
	 * @return
	 */
    public static Date getLastDateOfCurrentMonth(Date date) {
    	
    	if ( date == null ) {
    		return date;
    	}
    	
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
    }
  
    /**
     * Converts any String to a capitalized String,
     * including the removal of other capital letters
     * found in the String
     * @param str
     * @return
     */
    public static String capitalize(String str) {
    	
    	if ( str == null ) {
    		return str;
    	}
    	String[] splitWords = str.split(" ");
    	String[] returnStr = new String[splitWords.length];
    	
    	for ( int i=0; i < splitWords.length; i++ ) {
    		returnStr[i] = splitWords[i].substring(0,1).toUpperCase() + splitWords[i].substring(1).toLowerCase();
    	}
    	
    	return String.join(" ", returnStr);
    }
    
    /**
     * Returns an array of months from January to December first
     * of the input int year.
     * @param year
     * @return
     */
    public static String[] getMonthsArray(int year) {
    	
    	String[] monthsArray = new String[12];
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DATE, 1);
    	cal.set(Calendar.YEAR, year);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	
    	for ( int i=0; i < 12; i++ ) {
    		
    		cal.set(Calendar.MONTH, i);
        	
        	monthsArray[i] = sdf.format(cal.getTime());
    	}
    	
    	return monthsArray;
    }
}
