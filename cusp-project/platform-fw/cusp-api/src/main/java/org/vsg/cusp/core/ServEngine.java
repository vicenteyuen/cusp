/**
 * 
 */
package org.vsg.cusp.core;

import java.util.Map;

/**
 * @author Vicente Yuen
 *
 */
public interface ServEngine extends Service {
	
	/**
	 * start serv engine
	 */
	void init(Map<String,String> arguments);
	
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

}
