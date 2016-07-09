package org.vsg.cusp.concurrent;

import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.event.flow.Promise;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;

public interface EventFlow<T> {
	
	/**
	 * get he operation event for method ---
	 * @param eventName
	 * @return
	 */
	OperationEvent getOperEvent(String eventName);
	
	/**
	 * fire event when the event flow is end handle
	 * @param handle
	 */
	void fireAtEnd(Handler<AsyncResult> handle);
	
	/**
	 * define event flow manager
	 * @return
	 */
	FlowManager getFlowManager();
	
	
	public static final byte MODE_LOCAL = 0;
	
	public static final byte MODE_DIS = 1;
	
	Promise promise(byte mode);
	
	public static final String EVB_CHANNEL = "EvB_CHAN";
	
	EventBus getEventBus();
	
	// start deffer event 
	void startDeffer();

}
