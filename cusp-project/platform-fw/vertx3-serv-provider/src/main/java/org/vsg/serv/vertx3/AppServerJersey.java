package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class AppServerJersey extends AbstractVerticle  {
	
	
	private static Logger logger = LoggerFactory.getLogger(AppServerJersey.class);
	
	private Map<String, JsonObject> products = new HashMap<>();
	
    public static void main( String[] args )
    {

    	DeploymentOptions deploymentOptions = new DeploymentOptions();
    	Vertx vertx = Vertx.vertx(new VertxOptions().setClustered(false));
	    vertx.deployVerticle("java-guice:com.englishtown.vertx.jersey.JerseyVerticle", deploymentOptions);
    	
    }
    
    
    public AppServerJersey() {
    	
    }

    	

 
    
}
