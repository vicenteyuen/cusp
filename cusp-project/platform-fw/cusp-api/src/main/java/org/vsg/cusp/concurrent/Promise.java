package org.vsg.cusp.concurrent;

public interface Promise<V> {
	
	// --- base promise call back
	Promise<V> addOperationEvent(OperationEvent event);

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
