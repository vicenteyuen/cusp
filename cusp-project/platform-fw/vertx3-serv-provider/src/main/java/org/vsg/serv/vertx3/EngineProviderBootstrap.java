package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.serv.api.EngineProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EngineProviderBootstrap implements EngineProvider {
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);	
	
	private ClassLoader runtimeClassLoader;
	
	public void setRuntimeClassLoader(ClassLoader runtimeClassLoader)  {
		this.runtimeClassLoader = runtimeClassLoader;
	}
	

	@Override
	public void start() {
		
		String verticleID = Vert3HttpServer.class.getName();
		
		VertxOptions options = new VertxOptions().setClustered(false);
		
		runServer(verticleID , options , null);
     


        
        //List<Class<?>>  clses  =  rcar.findCandidates("org.vsg", Path.class);
       
        
        //System.out.println("mod inst : " + clses);

	}
	

	private void runServer(String verticleID, VertxOptions options,
			DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		// Smart cwd detection

		Consumer<Vertx> runner = vertx -> {
			try {

				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		};

		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
	



}
