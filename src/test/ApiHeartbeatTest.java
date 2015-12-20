package test;

//import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.*;
//import org.junit.Test;
//import static org.hamcrest.Matchers.*;
//import com.jayway.restassured.module.jsv.JsonSchemaValidator.*;
//import static com.jayway.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

//import com.jayway.restassured.RestAssured.*;
//import com.jayway.restassured.matcher.RestAssuredMatchers.*;
//import org.hamcrest.Matchers.*;
//import com.jayway.restassured.module.jsv.JsonSchemaValidator.*


public class ApiHeartbeatTest {

	final String apiPath = "http://localhost:8080/CSWebProject/dataApi/heartbeat";
	
	
	@Test
	public void test() {
		
		  expect().
		    statusCode(200).
		    when().
		    get(apiPath).asString();
	}

}
