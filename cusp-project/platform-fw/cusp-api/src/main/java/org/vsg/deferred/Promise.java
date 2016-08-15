package org.vsg.deferred;

import org.vsg.cusp.core.Handler;

public interface Promise<D, F, P> {
	
	public enum State {
		/**
		 * The Promise is still pending - it could be created, submitted for execution,
		 * or currently running, but not yet finished.
		 */
		PENDING,
		
		/**
		 * The Promise has finished running and a failure occurred.
		 * Thus, the Promise is rejected.
		 * 
		 * @see Deferred#reject(Object)
		 */
		REJECTED,
		
		/**
		 * The Promise has finished running successfully.
		 * Thus the Promise is resolved.
		 * 
		 * @see Deferred#resolve(Object)
		 */
		RESOLVED
	}

	public State state();

	/**
	 * @see State#PENDING
	 * @return
	 */
	public boolean isPending();

	/**
	 * @see State#RESOLVED
	 * @return
	 */
	public boolean isResolved();

	/**
	 * @see State#REJECTED
	 * @return
	 */
	public boolean isRejected();	
	
	/**
	 * execute then object
	 * @param handler
	 * @return
	 */
	Promise<D, F, P> then(Handler<D> handler);
	
	/**
	 * 
	 * @param succeedCallback
	 * @return
	 */
	Promise<D, F, P> succeed(Handler<D> succeedCallback);
	
	/**
	 * 
	 * @param errorCallback
	 * @return
	 */
	Promise<D, F, P> fail(Handler<F> errorCallback);
	
	
	/**
	 * 
	 * @param callback
	 * @return
	 */
	Promise<D, F, P> progress(Handler<P> callback);
	
	

    Promise<D, F, P> await() throws InterruptedException;	
	

}
