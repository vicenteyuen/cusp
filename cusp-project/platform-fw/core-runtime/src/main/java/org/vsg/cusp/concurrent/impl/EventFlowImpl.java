package org.vsg.cusp.concurrent.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.eventbus.Handler;

public class EventFlowImpl implements EventFlow {

	@Override
	public OperationEvent getOperEvent(String eventName) {
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
