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
	public <D> Promise<D, Throwable, Void> when(Handler<D>... handlers) {

		Promise[] promises = new Promise[handlers.length];

		for (int i = 0; i < handlers.length; i++) {
			promises[i] = when(handlers[i]);
		}		
		
		return new MergedDeferredObject(promises);
	}

	@Override
	public <D> Promise<D, Throwable, Void> when(Handler<D> handler) {
		
		DeferredObject deferredObject = new DeferredObject();
		deferredObject.progress( handler );
		return deferredObject;
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
