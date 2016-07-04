package org.vsg.cusp.concurrent.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.GenericFutureListener;
import org.vsg.cusp.concurrent.Promise;

public class PromiseImpl implements Promise {

	
	private EventFlow flow;
	
	public EventFlow getFlow() {
		return flow;
	}

	public void setFlow(EventFlow flow) {
		this.flow = flow;
	}
	
	
	private ExecutorService execService;
	

	public ExecutorService getExecService() {
		return execService;
	}

	public void setExecService(ExecutorService execService) {
		this.execService = execService;
	}
	
	
	List<GenericFutureListener> supportListeners = new ArrayList<GenericFutureListener>();


	@Override
	public Promise addListener(GenericFutureListener listener) {
		// TODO Auto-generated method stub
		supportListeners.add(listener);
		return this;
	}
	

	@Override
	public Promise await() {
		
		// --- execute event ---
		
		

		return this;
	}

	@Override
	public Promise sync() {
		// TODO Auto-generated method stub
		
		
		for (GenericFutureListener lis : supportListeners) {
			
			Runnable run = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					try {
						lis.operationComplete(null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			};
			
			execService.submit( run );
		}
		
		
		
		return this;
	}

	
	
	
	



}
