package org.vsg.cusp.concurrent.impl;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.EventBusAware;
import org.vsg.cusp.eventbus.Handler;

public class MultiNodeEventFlowImpl implements EventFlow , EventBusAware {
	
	private FlowManager flowManager;
	
	private EventBus eventBus;
	
	@Override
	public FlowManager getFlowManager() {
		return flowManager;
	}

	public void setFlowManager(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	@Override
	public OperationEvent getOperEvent(String eventId) {
		// TODO Auto-generated method stub
		return flowManager.getOperEventById(eventId);
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void fireAtEnd(Handler handle) {
		// TODO Auto-generated method stub
		
	}
	
	
	private PromiseImpl prom;
	

	@Override
	public Promise promise(byte mode) {
		
		prom = new PromiseImpl();
		prom.setFlow( this );
		if (mode == 1) {
			
		} 
		else if (mode == 0) {
			
		}
		
		
		return prom;
	}


	@Override
	public void startDeffer() {
		// --- execute handle ---

	}

	
	
}
