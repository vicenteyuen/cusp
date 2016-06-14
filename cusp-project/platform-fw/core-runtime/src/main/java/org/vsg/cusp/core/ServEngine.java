/**
 * 
 */
package org.vsg.cusp.core;

import java.util.Map;

/**
 * @author Vicente Yuen
 *
 */
public interface ServEngine {
	
	/**
	 * start serv engine
	 */
	void start();
	
	void stop();
	
	void init(Map<String,String> arguments);
	
	void setRunningContainer(Container container);

}
