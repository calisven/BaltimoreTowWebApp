package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApiGeneralDataTest.class, ApiHeartbeatTest.class, ApiVehicleTest.class, DbConnectionTest.class,
		DbSingletonTest.class, HelperCapitalizeTest.class })
public class AllTests {
	
}
