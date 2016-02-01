package org.vsg.serv.vertx3;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.serv.api.EngineProvider;

public class EngineProviderBootstrap implements EngineProvider {
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);	
	
	private ClassLoader runtimeClassLoader;
	
	public void setRuntimeClassLoader(ClassLoader runtimeClassLoader)  {
		this.runtimeClassLoader = runtimeClassLoader;
	}
	

	@Override
	public void start() {
		
		
		Vert3HttpVerticle verticle = new Vert3HttpVerticle();
		
		//String verticleID = "org.vsg.serv.vertx3.Vert3HttpServer";
		VertxOptions options = new VertxOptions().setClustered(false);
		
		DeploymentOptions deploymentOptions = null;
	
		runServer(verticle , options , deploymentOptions);


	}
	

	private void runServer(Verticle verticleID, VertxOptions options,
			DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		
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
			
			if (logger.isInfoEnabled()) {
				logger.info("Startup under single node.");
			}
			
			// --- mapping ---
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
		
	}
	



}
