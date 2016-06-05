package org.vsg.cusp.startup;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class MainServerHook  {
	
	
	private static Logger logger = LoggerFactory.getLogger(MainServerHook.class);
	
	
    public static void main( String[] args )
    {
    	
    	MainServerHook hook = new MainServerHook();
    	
    	// --- start fontend server ----
    	hook.startFontendServer();


    }
    
    
    public MainServerHook() {
    	
    }
    
    private Map<String, java.io.Serializable> inputEnvProp = new LinkedHashMap<String, java.io.Serializable>();
    
    
    private void startFontendServer() {
    	
    	inputEnvProp.put(ServerConst.SERVER_FE_HOST, "localhost");
    	inputEnvProp.put(ServerConst.SERVER_FE_PORT, "7777");
    	
    	
    	// ---- send start up ---
    	FontendServer fs = new FontendServer();
    	fs.setEnvProps(inputEnvProp);
    	
    	
    	
    	fs.startup();
    	

    }
    

    
}
