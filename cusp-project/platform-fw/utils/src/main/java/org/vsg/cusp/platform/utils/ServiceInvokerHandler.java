package org.vsg.cusp.platform.utils;

import javax.ws.rs.container.AsyncResponse;

/**
 * 
 * @author vison
 *
 */
public interface ServiceInvokerHandler {
	
	
	void handle(Result invokeResult);
	

}
