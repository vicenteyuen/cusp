/**
 * 
 */
package org.vsg.cusp.event.flow.impl;

import javax.inject.Provider;

import org.vsg.cusp.event.flow.FlowManagerFactory;

/**
 * @author vison
 *
 */
public class FlowManagerFactoryProvider implements Provider<FlowManagerFactory> {
	
	
	private FlowManagerFactory factoryInst = FlowManagerFactory.getInstance();

	@Override
	public FlowManagerFactory get() {
		// TODO Auto-generated method stub
		return factoryInst;
	}

}
