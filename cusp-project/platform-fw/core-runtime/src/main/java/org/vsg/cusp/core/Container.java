package org.vsg.cusp.core;

import java.util.Map;

/**
 * 
 * @author ruanweibiao
 *
 */
public interface Container {
	
	/**
	 * set the component class loader handle 
	 * @return
	 */
	Map<String,ClassLoader> getComponentsClassLoader();
	
	/**
	 * 
	 * @return
	 */
	ClassLoader getParentClassLoader();

}
