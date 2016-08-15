/**
 * 
 */
package org.vsg.deferred.impl;

import java.util.concurrent.FutureTask;

import org.vsg.deferred.Promise;

/**
 * @author ruanweibiao
 *
 */
public class DeferredFutureTask<D, P>  extends FutureTask<D> {

	private DeferredObject deferredObj = new DeferredObject();
	
	
	public DeferredFutureTask(Runnable runnable, D result) {
		super(runnable, result);
		
	}

	public Promise<D, Throwable, P> promise() {
		return deferredObj;
	}	

}
