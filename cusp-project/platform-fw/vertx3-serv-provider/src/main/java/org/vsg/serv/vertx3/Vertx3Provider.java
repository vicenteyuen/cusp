package org.vsg.serv.vertx3;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author vison ruan
 *
 */
public class Vertx3Provider implements Provider<Vertx> {
	
	private VertxOptions options;
	
	private DeploymentOptions deploymentOptions;
	
	
	private static Logger logger = LoggerFactory.getLogger( Vertx3Provider.class );
	
	
	public Vertx3Provider() {
		
	}

	public Vertx3Provider(VertxOptions options,
			DeploymentOptions deploymentOptions) {
		super();
		this.options = options;
		this.deploymentOptions = deploymentOptions;
	}




	@Override
	public Vertx get() {
		// TODO Auto-generated method stub
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("vertx init options : " + options);
		}
		Vertx vertx = null;

		if (options.isClustered()) {
			
			// --- empty code ---
			

		}
		else {
			
			vertx = Vertx.vertx(options);			
		}
		
		
		return vertx;
	}
	


}
