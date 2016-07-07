package org.vsg.cusp.event.flow;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.OperationEvent;


public interface FlowManager {
	
	
	EventFlow getFlow(String flowId);
	
	/**
	 * get operation event by id
	 * @param eventId
	 * @return
	 */
	OperationEvent getOperEventById(String eventId) ;
	
	

}
