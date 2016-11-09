/**
 * 
 */
package org.vsg.deferred.impl;

import org.vsg.deferred.Callback;
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
	public <D> Promise<D, Throwable, Void> when(Callback<D>... Callbacks) {

		Promise[] promises = new Promise[Callbacks.length];

		for (int i = 0; i < Callbacks.length; i++) {
			promises[i] = when(Callbacks[i]);
		}		
		
		return new MergedDeferredObject(promises);
	}

	@Override
	public <D> Promise<D, Throwable, Void> when(Callback<D> Callback) {
		
		DeferredObject deferredObject = new DeferredObject();
		deferredObject.progress( Callback );
		return deferredObject;
	}

	
	

	@Override
	public <D> Promise<D, Throwable, Void> succeed(Callback<D> succeedCallback) {
		return null;
	}

	@Override
	public <D> Promise<D, Throwable, Void> fail(Callback<D> failCallback) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}
