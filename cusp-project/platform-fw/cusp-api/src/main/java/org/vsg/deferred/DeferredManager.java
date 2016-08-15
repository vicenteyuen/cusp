package org.vsg.deferred;

import org.vsg.cusp.core.Handler;



public interface DeferredManager {
	
	<D> Promise<D, Throwable, Void> when(Handler<D>... futures);

	/**
	 * define handle
	 * @param handler
	 * @return
	 */
	<D> Promise<D, Throwable, Void> when(Handler<D> handler);
	

	<D> Promise<D, Throwable, Void> succeed(Handler<D> succeedHandler);
	
	
	<D> Promise<D, Throwable, Void> fail(Handler<D> failHandler);	
}
