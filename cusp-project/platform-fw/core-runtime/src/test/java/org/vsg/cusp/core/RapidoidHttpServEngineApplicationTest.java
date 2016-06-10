package org.vsg.cusp.core;

import java.util.LinkedHashMap;
import java.util.Map;


public class RapidoidHttpServEngineApplicationTest {
	
	private static RapidoidHttpServEngine engine = new RapidoidHttpServEngine();
	

	public void startServer() throws Exception {
		Map<String,String> arguments = new LinkedHashMap<String,String>();
		arguments.put("host", "localhost");
		arguments.put("port", "8100");
		
		engine.init(arguments);

		
		engine.start();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RapidoidHttpServEngineApplicationTest testCase = new RapidoidHttpServEngineApplicationTest();
		
		try {
			testCase.startServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
