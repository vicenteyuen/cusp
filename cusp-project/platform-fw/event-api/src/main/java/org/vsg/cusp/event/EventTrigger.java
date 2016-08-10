/**
 * 
 */
package org.vsg.cusp.event;

import org.vsg.cusp.concurrent.Callback;

/**
 * @author Vicente Yuen
 *
 */
public interface EventTrigger {
	
	/**
	 * fire event when the call param handle
	 * @param eventName
	 * @param params
	 */
	<T> void fire(String eventName , RuntimeParams params ,  Callback<T> callback) throws Exception;

}
