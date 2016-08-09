/**
 * 
 */
package org.vsg.cusp.concurrent;

/**
 * @author ruanweibiao
 *
 */
public interface PromiseFactory {
	
		
	<T> Promise<T> getPromise();

}
