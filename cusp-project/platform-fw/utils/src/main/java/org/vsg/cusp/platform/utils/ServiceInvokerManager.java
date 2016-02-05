package org.vsg.cusp.platform.utils;

import java.util.Map;

/**
 * define service invoker 
 * @author vison ruan
 *
 */
public interface ServiceInvokerManager {
	
	
	void invokeService(String serviceName , Map<?,?> params,ServiceInvokerHandler handler) throws ServiceInvokerException;

}
