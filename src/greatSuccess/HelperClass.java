package greatSuccess;

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
}
