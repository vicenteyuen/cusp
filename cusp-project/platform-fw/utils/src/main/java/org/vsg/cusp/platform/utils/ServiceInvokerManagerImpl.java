/**
 * 
 */
package org.vsg.cusp.platform.utils;

import io.vertx.core.Vertx;

import javax.inject.Inject;


/**
 * @author vison
 *
 */
public class ServiceInvokerManagerImpl implements ServiceInvokerManager {
	
	// --- get the proxy ---
	@Inject
	private Vertx vertx;	

	/* (non-Javadoc)
	 * @see utils.ServiceInvokerManager#invokeService(java.lang.String, utils.ServiceInvokerHandler)
	 */
	@Override
	public void invokeService(String serviceName, ServiceInvokerHandler handler)
			throws ServiceInvokerException {
		
		if (serviceName.startsWith("service:")){
			// call service method
			handleByVertx(serviceName , handler);
		}
		


	}
	
	
	private void handleByVertx(String serviceName, ServiceInvokerHandler handler) {
		vertx.deployVerticle(serviceName , res -> {
			
			Result result = new Result();
			result.setSucess( res.succeeded() );
			// --- calll handle value ---
			handler.handle( result );
		
			
			
		});		
	}

}
