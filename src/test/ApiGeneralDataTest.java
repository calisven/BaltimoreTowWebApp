package test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ApiGeneralDataTest {

	final String apiPath = "http://localhost:8080/CSWebProject/dataApi/monthlyTows";
	
	@Test
	public void testQueryNoMake() {
		
		given().
		expect().
	    statusCode(200).
	    when().
	    get(apiPath);
	}
	
	@Test
	public void testQueryReturnValue() {
		
		given().
		expect().
	    statusCode(200).
	    body(
	       "2014.June", equalTo(2688),
	  	   "2015.June", equalTo(2531),
	  	   "2014Total", equalTo(31248),
	  	   "2015Total", equalTo(27842),
	  	   "combinedTotal", equalTo(59090),
	  	   "2014Paid.May", equalTo(265),
	  	   "2015Paid.July", equalTo(255),
	  	   "2014Stolen.February", equalTo(171),
	  	   "2015Stolen.March", equalTo(138)
	  	).
	    when().
	    get(apiPath);
	}

}
