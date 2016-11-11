package org.vsg.cusp.platform.api;

import java.io.File;
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
	
	
	Map<String, File> getComponentsPath();

}
