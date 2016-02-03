package org.vsg.cusp.engine.vertx3;

import java.util.function.Consumer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.serv.vertx3.Vert3HttpVerticle;

public class BootstrapServletContextListener implements ServletContextListener {
	
	private static Logger logger = LoggerFactory.getLogger( BootstrapServletContextListener.class );	
	
	
	// --- build vertx context

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		try {
			Vert3HttpVerticle verticle = new Vert3HttpVerticle();
			
			VertxOptions options = new VertxOptions().setClustered(false);
			
			DeploymentOptions deploymentOptions = null;
			
			runServer(verticle , options , deploymentOptions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

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
