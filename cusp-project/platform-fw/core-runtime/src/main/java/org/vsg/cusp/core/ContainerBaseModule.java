/**
 * 
 */
package org.vsg.cusp.core;

import com.google.inject.AbstractModule;

/**
 * @author Vicente Yuen
 *
 */
public abstract class ContainerBaseModule extends AbstractModule {

	private Container container;
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}
