package org.vsg.cusp.concurrent.impl;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.event.flow.Promise;
import org.vsg.cusp.event.impl.PromiseImpl;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;

public class SingleNodeEventFlowImpl implements EventFlow {
	
	private FlowManager flowManager;
	
	@Override
	public FlowManager getFlowManager() {
		return flowManager;
	}

	public void setFlowManager(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	@Override
	public OperationEvent getOperEvent(String eventName) {
		// TODO Auto-generated method stub
		return flowManager.getOperEventById(eventName);
	}


	@Override
	public EventBus getEventBus() {
		// TODO Auto-generated method stub
		return null;
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
