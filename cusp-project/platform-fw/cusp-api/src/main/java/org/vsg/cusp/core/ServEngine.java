/**
 * 
 */
package org.vsg.cusp.core;

import java.util.Map;

/**
 * @author Vicente Yuen
 *
 */
public interface ServEngine extends Service{
	
	/**
	 * start serv engine
	 */
	void init(Map<String,String> arguments);

}
