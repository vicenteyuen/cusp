/**
 * 
 */
package org.vsg.cusp.platform.core.api;

import java.util.Map;

import org.vsg.common.async.AsyncResult;
import org.vsg.common.async.Callback;
import org.vsg.cusp.platform.api.LifecycleState;

/**
 * @author Vicente Yuen
 *
 */
public interface ServEngine extends Service {
	
	/**
	 * start serv engine
	 */
	<T extends Object> void init(Map<String,String> arguments , Callback<AsyncResult<T>> callback);
	
	/**
	 * get service state 
	 * @return
	 */
	LifecycleState getState();
	
	/**
	 * reset new state
	 * @param newState
	 */
	void setState(LifecycleState newState);
	
	/**
	 * stop destory handle 
	 */
	<T extends Object> void destory(Callback<AsyncResult<T>> callback);

}
