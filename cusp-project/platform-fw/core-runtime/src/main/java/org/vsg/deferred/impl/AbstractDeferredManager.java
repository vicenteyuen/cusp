/**
 * 
 */
package org.vsg.deferred.impl;

import org.vsg.cusp.core.Handler;
import org.vsg.deferred.DeferredManager;
import org.vsg.deferred.Promise;

/**
 * @author ruanweibiao
 *
 */
public abstract class AbstractDeferredManager implements DeferredManager {

	/* (non-Javadoc)
	 * @see org.vsg.deferred.DeferredManager#when(java.util.concurrent.Future)
	 */
	@Override
	public <D> Promise<D, Throwable, Void> when(Handler<D>... futures) {

		return null;
	}

	@Override
	public <D> Promise<D, Throwable, Void> when(Handler<D> handler) {
		
		Runnable runHandler = () -> {
			handler.handle(null);
		};
		
		DeferredFutureTask task = new DeferredFutureTask(runHandler, null);
		when(task);

		return when(task);
	}
	
	private <D> Promise<D, Throwable, Void> when(DeferredFutureTask task ) {
		/*
		if (task.getStartPolicy() == StartPolicy.AUTO 
				|| (task.getStartPolicy() == StartPolicy.DEFAULT && isAutoSubmit())) {
			//submit(task);			
		}
		*/
		Promise<D, Throwable, Void> promise = task.promise();
	
		return promise;
		
	}
	
	

	@Override
	public <D> Promise<D, Throwable, Void> succeed(Handler<D> succeedHandler) {
		return null;
	}

	@Override
	public <D> Promise<D, Throwable, Void> fail(Handler<D> failHandler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}
