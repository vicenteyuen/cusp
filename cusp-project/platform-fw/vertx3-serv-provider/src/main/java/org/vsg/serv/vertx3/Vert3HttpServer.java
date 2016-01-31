package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Vert3HttpServer extends AbstractVerticle {
	
	private static Logger logger = LoggerFactory.getLogger(Vert3HttpServer.class);	
	
    // ---- run vertx server ---
    @Override
    public void start() throws Exception {
		// TODO Auto-generated method stub
		Set<String> scanPackages = new LinkedHashSet<String>();
		scanPackages.add("org.vsg");
		

        Router router = Router.router(vertx);			
		/*
    	Injector injector = Guice.createInjector(
    			new JsrRestControllerModule(router , runtimeClassLoader , scanPackages));
    			*/
	
		
        //JsrRestControllerModule  modInst =  injector.getInstance(JsrRestControllerModule.class);
        System.out.println(vertx);
    	HttpServer  httpServer = vertx.createHttpServer();
    	System.out.println(httpServer + " , " + router);

    	httpServer.requestHandler(router::accept).listen(8080);
        logger.info("Startuped server on port : [8080]");   	    	
    }
}