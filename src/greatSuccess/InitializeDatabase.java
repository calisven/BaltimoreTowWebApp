package greatSuccess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.codehaus.jettison.json.JSONObject;

public class InitializeDatabase implements ServletContextListener{

	ServletContext context;
	
	public InitializeDatabase() {
		
		// TODO Auto-generated method stub
//		try {
//			 System.out.println ("trying..");
 
			// Step2: Now pass JSON File Data to REST Service
//			try {
//				URL url = new URL("http://localhost:8080/CSWebProject/dataApi/mainData");
//				URLConnection connection = url.openConnection();
//				connection.setDoOutput(true);
//				connection.setRequestProperty("Content-Type", "application/json");
//				connection.setConnectTimeout(5000);
//				connection.setReadTimeout(5000);
//				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//				out.write(jsonObject.toString());
//				out.close();
// 
//				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
// 
//				while (in.readLine() != null) {
//				}
//				System.out.println("\nCrunchify REST Service Invoked Successfully..");
//				in.close();
//			} catch (Exception e) {
//				System.out.println("\nError while calling Crunchify REST Service");
//				System.out.println(e);
//			}
// 
//			br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		System.out.println("Ended!");

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		System.out.println("Started!");
		
	}

}
