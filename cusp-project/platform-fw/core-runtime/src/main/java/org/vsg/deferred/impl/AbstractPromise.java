package org.vsg.deferred.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Handler;
import org.vsg.deferred.Promise;

public class AbstractPromise<D, F, P> implements Promise<D, F, P> {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractPromise.class);
	
	protected volatile State state = State.PENDING;
	
	protected D resolveResult;
	
	protected F rejectResult;
	
	private List<Handler<D>> doneCallbacks = new CopyOnWriteArrayList<Handler<D>>();
	private List<Handler<F>> failCallbacks = new CopyOnWriteArrayList<Handler<F>>();
	private List<Handler<P>> progressCallbacks = new CopyOnWriteArrayList<Handler<P>>();

	

	@Override
	public State state() {
		return state;
	}

	@Override
	public boolean isPending() {
		return state() == State.PENDING;
	}

	@Override
	public boolean isResolved() {
		return state() == State.RESOLVED;
	}

	@Override
	public boolean isRejected() {
		return state() == State.REJECTED;
	}

	@Override
	public Promise<D, F, P> then(Handler<D> handler) {
		return this;
	}

	@Override
	public Promise<D, F, P> succeed(Handler<D> callback) {
		
		synchronized (this) {
			if (isResolved()){
				triggerSucceed(callback, resolveResult);
			}else{
				doneCallbacks.add(callback);
			}
		}
		
		return this;
	}

	private void triggerSucceed(Handler<D> callback, D resolved) {
		callback.handle( resolved );
	}
	
	
	@Override
	public Promise<D, F, P> fail(Handler<F> errorCallback) {
		
		synchronized (this) {
			if(isRejected()){
				triggerFail(errorCallback, rejectResult);
			}else{
				failCallbacks.add(errorCallback);
			}
		}		
		
		return this;
	}
	
	protected void triggerFail(Handler<F> callback, F rejected) {
		callback.handle(rejected);
	}
	

	@Override
	public Promise<D, F, P> progress(Handler<P> callback) {
		progressCallbacks.add(callback);		
		return this;
	}
	
	private void triggerProgress(Handler<P> callback, P progress) {
		callback.handle(progress);
	}
	
	
	

	@Override
	public Promise<D, F, P> await() throws InterruptedException {
		
		/**
		 * execute progress handle 
		 */
		
		return this;
	}
	
	

}
