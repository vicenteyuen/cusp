package org.vsg.cusp.event.flow;

import org.vsg.cusp.core.ServiceHelper;

/**
 * Pre define 
 * @author Vicente Yuen
 *
 */
public interface FlowManagerFactory {
	
	/**
	 * get the base flow manager
	 * @return
	 */
	FlowManager getManager();
	
	
	
	public static FlowManagerFactory getInstance() {
		FlowManagerFactory  factory =  ServiceHelper.loadFactory( org.vsg.cusp.event.flow.FlowManagerFactory.class );
		return factory;
	}

}
