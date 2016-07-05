package org.vsg.cusp.concurrent;


public interface EventFlowManager {
	
	
	EventFlow getFlow(String flowId);
	
	/**
	 * get operation event by id
	 * @param eventId
	 * @return
	 */
	OperationEvent getOperEventById(String eventId) ;

}
