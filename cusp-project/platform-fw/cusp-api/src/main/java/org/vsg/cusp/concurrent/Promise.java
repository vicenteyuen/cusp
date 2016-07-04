package org.vsg.cusp.concurrent;

import java.util.concurrent.Future;


public interface Promise<V> {
	
	// --- base promise call back
	Promise addListener(GenericFutureListener<? extends Future<? super V>> listener);

	/**
	 * Waits for this future to be completed
	 * @return
	 * @throws InterruptedException
	 */
	Promise await() throws InterruptedException;

	/**
	 * Waits for this future until it is done, and rethrows the cause of the failure if this future failed.
	 * @return
	 * @throws InterruptedException
	 */
	Promise sync() throws InterruptedException;

}
