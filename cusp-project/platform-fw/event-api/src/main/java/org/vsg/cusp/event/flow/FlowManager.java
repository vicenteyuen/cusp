package org.vsg.cusp.event.flow;

import org.vsg.cusp.event.OperationEvent;
import org.vsg.cusp.eventbus.EventFlow;


public interface FlowManager {
	
	
	EventFlow getFlow(String flowId);
	
	/**
	 * get operation event by id
	 * @param eventId
	 * @return
	 */
	OperationEvent getOperEventById(String eventId) ;
	
	

}
