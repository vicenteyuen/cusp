/**
 * 
 */
package org.vsg.cusp.serv.api;

/**
 * @author Vison Ruan
 *
 */
public interface EngineProvider {
	
	/**
	 * start service
	 */
	void start();
	
	
	void setRuntimeClassLoader(ClassLoader clsLoader);

}
