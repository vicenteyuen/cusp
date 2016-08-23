package org.vsg.deferred;




public interface DeferredManager {
	
	<D> Promise<D, Throwable, Void> when(Callback<D>... futures);

	/**
	 * define handle
	 * @param handler
	 * @return
	 */
	<D> Promise<D, Throwable, Void> when(Callback<D> handler);
	

	<D> Promise<D, Throwable, Void> succeed(Callback<D> succeedHandler);
	
	
	<D> Promise<D, Throwable, Void> fail(Callback<D> failHandler);	
}
