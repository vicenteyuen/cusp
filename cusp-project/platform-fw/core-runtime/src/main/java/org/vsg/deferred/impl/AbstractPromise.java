package org.vsg.deferred.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Handler;
import org.vsg.deferred.Promise;

public abstract class AbstractPromise<D, F extends Throwable, P> implements Promise<D, F, P> {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractPromise.class);
	
	protected volatile State state = State.PENDING;
	
	protected D resolveResult;
	
	protected F rejectResult;
	
	private List<Handler<D>> doneCallbacks = new CopyOnWriteArrayList<Handler<D>>();
	private List<Handler<Throwable>> failCallbacks = new CopyOnWriteArrayList<Handler<Throwable>>();
	private List<Handler<P>> progressCallbacks = new CopyOnWriteArrayList<Handler<P>>();

	private List<ActivityUnit> scheActivityUnits = new CopyOnWriteArrayList<ActivityUnit>();
	
	
	private boolean commited;
	
	
	private void setCommited(boolean commited) {
		this.commited = commited;
	}
	
	private boolean getCommited() {
		return this.commited;
	}

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
	public Promise<D, F, P> then(Handler<P> handler) {
		// --- set commit ---
		
		
		return this;
	}

	@Override
	public Promise<D, F, P> succeed(Handler<D> callback) {
		doneCallbacks.add(callback);
		return this;
	}

	private void triggerSucceed(Handler<D> callback, D resolved) {
		callback.handle( resolved );
	}
	
	
	@Override
	public Promise<D, F, P> fail(Handler<F> errorCallback) {
		failCallbacks.add((Handler)errorCallback);

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
	public Promise<D, F, P> then(Handler<P> handler, Handler<D> succeedHandler,
			Handler<F> failHandler) {
		
		this.setCommited(true);
		
		this.progressCallbacks.add(handler);
		// --- create activityUnit ----
		notifyActivityUnitCollection((Handler<?>)succeedHandler , (Handler<Throwable>)failHandler);
		
		this.setCommited(false);

		return this;
	}
	
	private short seqNo = 0;
	
	/**
	 * nodify to create activity unit collection
	 */
	private void notifyActivityUnitCollection(Handler<?> doneHandler , Handler<Throwable> failHandler) {
		ActivityUnit activityUnit = new ActivityUnit();
		activityUnit.setSequenceOrder( seqNo++ );
		/**
		 * copy progress call back
		 */
		if (!this.progressCallbacks.isEmpty()) {

			activityUnit.setProcessHandlers( this.progressCallbacks.toArray(new Handler[0]) );
		}
		
		activityUnit.setThenDoneHandler(doneHandler);
		activityUnit.setThenFailHandler( failHandler );

		if (end) {
			
			if (!this.doneCallbacks.isEmpty()) {
				activityUnit.setDoneHandlers( this.doneCallbacks.toArray( new Handler[0] ) );			
			}
			
			if (!this.failCallbacks.isEmpty()) {
				activityUnit.setFailHandlers( this.failCallbacks.toArray(new Handler[0]) );				
			}
			
			this.doneCallbacks.clear();
			
			this.failCallbacks.clear();
			
		}		
		
		
		scheActivityUnits.add( activityUnit );
		
		
		// --- clear progress back ---
		this.progressCallbacks.clear();

		
	}
	
	
	private boolean end;
	
	

	@Override
	public Promise<D, F, P> await() throws InterruptedException {
		
		
		/**
		 * comit all activity units
		 */
		this.setCommited(true);
		this.end = true;
		
		notifyActivityUnitCollection(null, null);
		
		this.setCommited(false);
		
		
		System.out.println( this.scheActivityUnits );
		
		/*
	    CountDownLatch doneSignal = new CountDownLatch(progressCallbacks.size());
		
		for (int calllbackCounter = progressCallbacks.size()-1 ; calllbackCounter >= 0 ; calllbackCounter--) {
			Handler<P> handlerEvent = progressCallbacks.get(calllbackCounter);
			
			Runnable runTask = () -> {
				handlerEvent.handle(null);
				doneSignal.countDown();
			};
			
			Thread executeThread = new Thread(runTask);
			executeThread.start();
		}
		
		doneSignal.await();
		
		
		
		// --- check if success ,call success event ---
		for (int calllbackCounter = this.doneCallbacks.size()-1 ; calllbackCounter >= 0 ; calllbackCounter--) {
			Handler<D> handlerEvent = doneCallbacks.get(calllbackCounter);
			
			Runnable runTask = () -> {
				handlerEvent.handle(null);
			};
			
			Thread executeThread = new Thread(runTask);
			executeThread.start();			
		}
		*/

		
		
		
		return this;
	}
	
	

}
