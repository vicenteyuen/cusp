/**
 * 
 */
package org.vsg.deferred.impl;

import java.util.concurrent.CompletableFuture;

import org.vsg.deferred.Promise;

/**
 * @author ruanweibiao
 *
 */
public class DeferredObject<D, F, P> extends AbstractPromise<D, F, P> {
	
	private CompletableFuture completableFuture = new CompletableFuture();
	

	public Promise<D, F, P> promise() {
		return this;
	}	
	
}
