/**
 * 
 */
package org.vsg.cusp.platform.runtime;

import com.google.inject.Injector;

/**
 * @author vison
 *
 */
public interface GuiceInjectorAware {
	
	
	void setParentInjector(Injector injector);

}
