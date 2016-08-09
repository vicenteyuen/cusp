package org.vsg.cusp.concurrent;

import org.vsg.cusp.eventbus.spi.ServiceHelper;



public interface Promise<V> extends Callback<V>{
	
	
	static final PromiseFactory factory = ServiceHelper.loadFactory(PromiseFactory.class);
	  
	/*
	void setFlow(EventFlow eventFlow);
	
	
	// --- base promise call back
	Promise<V> addOperationEvent(OperationEvent event);
	*/

	/**
	 * Waits for this future to be completed
	 * @return
	 * @throws InterruptedException
	 */
	Promise<V> await() throws InterruptedException;

	/**
	 * Waits for this future until it is done, and rethrows the cause of the failure if this future failed.
	 * @return
	 * @throws InterruptedException
	 */
	Promise<V> sync() throws InterruptedException;

}
