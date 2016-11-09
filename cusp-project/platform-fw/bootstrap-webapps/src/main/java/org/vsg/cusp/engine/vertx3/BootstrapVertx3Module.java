package org.vsg.cusp.engine.vertx3;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.service.ServiceVerticleFactory;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.platform.utils.ServiceInvokerManager;
import org.vsg.cusp.platform.utils.ServiceInvokerManagerImpl;

import com.google.inject.AbstractModule;

public class BootstrapVertx3Module extends AbstractModule {

	private static Logger logger = LoggerFactory.getLogger( BootstrapVertx3Module.class );	
	
	
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
/*		try {
			Vert3HttpVerticle verticle = new Vert3HttpVerticle();
			
			VertxOptions options = new VertxOptions().setClustered(false);
			
			DeploymentOptions deploymentOptions = null;
			
			//runServer(verticle , options , deploymentOptions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		VertxOptions options = new VertxOptions().setClustered(false);
		
		DeploymentOptions deploymentOptions = null;
		
		//Vertx3Provider vertxProv = new Vertx3Provider(options , deploymentOptions);
		/*
		Vertx vertx = vertxProv.get();		
		// --- bind service factory ---
		vertx.registerVerticleFactory( new ServiceVerticleFactory() );
		
		// --- bind object
		this.bind(Vertx.class).toInstance(vertx);
		*/
		
		//this.bind(org.vsg.cusp.apps.productmng.ProductRest.class);
		this.bind( org.vsg.cusp.apps.system.UserRest.class );
		
		ServiceInvokerManagerImpl simi = new ServiceInvokerManagerImpl();
		this.bind(ServiceInvokerManager.class).toInstance(simi);

		
		
	}
	
	
	
	// --- bind object ---
	
	
	
	
	
	

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
