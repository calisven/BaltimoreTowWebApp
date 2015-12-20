package test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class ApiVehicleTest {

	final String apiPath = "http://localhost:8080/CSWebProject/dataApi/vehicle/";
	
	@Test
	public void testQueryNoMake() {
		
		given().
        queryParam("vehicleMake", "").
		expect().
	    statusCode(400).
	    when().
	    get(apiPath);
	}
	
	@Test
	public void testQueryNoModel() {
		
		given().
        queryParam("vehicleModel", "").
        queryParam("vehicleMake", "Ford").
		expect().
	    statusCode(200).
	    when().
	    get(apiPath);

	}
	
	@Test
	public void testQueryBasic() {
		given().
		queryParam("vehicleMake", "Ford").
		expect().
	    statusCode(200).
	    body(
	      "2014", hasSize(greaterThan(0)),
	      "2015", hasSize(greaterThan(0))
	      ).
	    when().
	    get(apiPath);
	}
	
	@Test
	public void testQueryMakeModel() {
		given().
		queryParam("vehicleMake", "Ford").
		queryParam("vehicleModel", "Mustang").
		expect().
	    statusCode(200).
	    body(
	      "2014", hasSize(greaterThan(0)),
	      "2015", hasSize(greaterThan(0))
	      ).
	    when().
	    get(apiPath);
	}

}
