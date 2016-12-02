package org.vsg.cusp.platform.api;

import java.io.File;
import java.util.Map;
import java.util.Properties;

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
	
	
	Map<String, Properties> getComponentsProps();

}
