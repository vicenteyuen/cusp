/**
 * 
 */
package org.vsg.cusp.serv.api;

/**
 * @author Vison Ruan
 *
 */
public interface ServiceProvider {
	
	/**
	 * start service
	 */
	void start();
	
	
	void setRuntimeClassLoader(ClassLoader clsLoader);

}
