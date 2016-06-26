/**
 * 
 */
package org.vsg.cusp.core.modules;

import org.vsg.cusp.core.Container;

import com.google.inject.AbstractModule;

/**
 * @author Vicente Yuen
 *
 */
public abstract class AbstractContainerModule extends AbstractModule {

	private Container container;
	
	public Container getRunningContainer() {
		return container;
	}

	public void setRunningContainer(Container container) {
		this.container = container;
	}

}
