package org.vsg.cusp.bootstrap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.HttpMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class AppServer extends AbstractVerticle  {
	
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);
	
	private Map<String, JsonObject> products = new HashMap<>();
	
    public static void main( String[] args )
    {
    	Runner.runExample(AppServer.class);
    }
    
    
    public AppServer() {
    	
    }
    
    
    // ---- run vertx server ---
    @Override
    public void start() throws Exception {
    	
    	
    }
    
}
