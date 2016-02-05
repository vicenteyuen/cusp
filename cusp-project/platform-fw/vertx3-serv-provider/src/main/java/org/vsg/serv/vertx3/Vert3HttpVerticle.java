package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vert3HttpVerticle extends AbstractVerticle {
	
	private static Logger logger = LoggerFactory.getLogger(Vert3HttpVerticle.class);	
	
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

    	HttpServer  httpServer = vertx.createHttpServer();

    	httpServer.requestHandler(router::accept).listen(8090);
        logger.info("Startuped server on port : [8090]");   	
   	
    }

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		// TODO Auto-generated method stub

		super.start(startFuture);
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		// TODO Auto-generated method stub
		super.stop(stopFuture);
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
    
    
    
}