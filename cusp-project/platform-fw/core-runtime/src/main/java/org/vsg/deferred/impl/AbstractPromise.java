package org.vsg.deferred.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Handler;
import org.vsg.deferred.Promise;

public abstract class AbstractPromise<D, F extends Throwable, P> implements
		Promise<D, F, P> {

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
		// callback.handle( resolved );
	}

	@Override
	public Promise<D, F, P> fail(Handler<F> errorCallback) {
		failCallbacks.add((Handler) errorCallback);

		return this;
	}

	protected void triggerFail(Handler<F> callback, F rejected) {
		// callback.handle(rejected);
	}

	@Override
	public Promise<D, F, P> progress(Handler<P> callback) {
		progressCallbacks.add(callback);
		return this;
	}

	private void triggerProgress(Handler<P> callback, P progress) {
		// callback.handle(progress);
	}

	@Override
	public Promise<D, F, P> then(Handler<P> handler, Handler<D> succeedHandler,
			Handler<F> failHandler) {

		this.setCommited(true);

		this.progressCallbacks.add(handler);
		// --- create activityUnit ----
		notifyActivityUnitCollection((Handler<?>) succeedHandler,
				(Handler<Throwable>) failHandler);

		this.setCommited(false);

		return this;
	}

	private short seqNo = 0;

	/**
	 * nodify to create activity unit collection
	 */
	private void notifyActivityUnitCollection(Handler<?> doneHandler,
			Handler<Throwable> failHandler) {
		ActivityUnit activityUnit = new ActivityUnit();
		activityUnit.setSequenceOrder(seqNo++);
		/**
		 * copy progress call back
		 */
		if (!this.progressCallbacks.isEmpty()) {

			activityUnit.setProcessHandlers(this.progressCallbacks
					.toArray(new Handler[0]));
		}

		activityUnit.setThenDoneHandler(doneHandler);
		activityUnit.setThenFailHandler(failHandler);

		if (end) {

			if (!this.doneCallbacks.isEmpty()) {
				activityUnit.setDoneHandlers(this.doneCallbacks
						.toArray(new Handler[0]));
			}

			if (!this.failCallbacks.isEmpty()) {
				activityUnit.setFailHandlers(this.failCallbacks
						.toArray(new Handler[0]));
			}

			this.doneCallbacks.clear();

			this.failCallbacks.clear();

		}

		scheActivityUnits.add(activityUnit);

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

		doRun();

		/*
		 * CountDownLatch doneSignal = new
		 * CountDownLatch(progressCallbacks.size());
		 * 
		 * for (int calllbackCounter = progressCallbacks.size()-1 ;
		 * calllbackCounter >= 0 ; calllbackCounter--) { Handler<P> handlerEvent
		 * = progressCallbacks.get(calllbackCounter);
		 * 
		 * Runnable runTask = () -> { handlerEvent.handle(null);
		 * doneSignal.countDown(); };
		 * 
		 * Thread executeThread = new Thread(runTask); executeThread.start(); }
		 * 
		 * doneSignal.await();
		 * 
		 * 
		 * 
		 * // --- check if success ,call success event --- for (int
		 * calllbackCounter = this.doneCallbacks.size()-1 ; calllbackCounter >=
		 * 0 ; calllbackCounter--) { Handler<D> handlerEvent =
		 * doneCallbacks.get(calllbackCounter);
		 * 
		 * Runnable runTask = () -> { handlerEvent.handle(null); };
		 * 
		 * Thread executeThread = new Thread(runTask); executeThread.start(); }
		 */

		return this;
	}

	private void doRun() {

		ActivityUnit[] activityUnits = this.scheActivityUnits
				.toArray(new ActivityUnit[0]);

		Collection<Handler<?>> doneHandlerColl = new Vector<Handler<?>>();
		Collection<Handler<Throwable>> failHandlerColl = new Vector<Handler<Throwable>>();

		List<Throwable> runningThrowables = new ArrayList<Throwable>();

		/**
		 * pre arrange activity unit handle
		 */
		for (int i = 0; i < activityUnits.length; i++) {
			Handler<?>[] doneHandlers = activityUnits[i].getDoneHandlers();
			Handler<Throwable>[] failHandlers = activityUnits[i]
					.getFailHandlers();

			if (null != doneHandlers) {
				for (int j = 0; j < doneHandlers.length; j++) {
					doneHandlerColl.add(doneHandlers[j]);
				}
			}

			if (null != failHandlers) {
				for (int j = 0; j < failHandlers.length; j++) {
					failHandlerColl.add(failHandlers[j]);
				}
			}
		}

		try {
			
			for (int i = 0; i < activityUnits.length; i++) {
				List<Throwable> thenException = new Vector<Throwable>();
				
				Handler<?>[] processHandlers = activityUnits[i].getProcessHandlers();

				/**
				 * process handle
				 */
				if (null != processHandlers) {

					CountDownLatch procSignal = new CountDownLatch(
							processHandlers.length);

					for (int j = 0; j < processHandlers.length; j++) {

						Handler<?> handlerEvent = processHandlers[j];

						Runnable runTask = () -> {
							try {
								handlerEvent.handle(null);
							} catch (Throwable e) {
								runningThrowables.add(e);
							} finally {
								procSignal.countDown();
							}
						};

						Thread executeThread = new Thread(runTask);
						executeThread.start();

					}

					procSignal.await();

				}

				/**
				 * handle done finish event
				 */
				if (null != activityUnits[i].getThenDoneHandler()) {
					Handler<?> doneHandler = activityUnits[i]
							.getThenDoneHandler();
					Runnable runTask = () -> {
						try {
							doneHandler.handle(null);
						} catch (Throwable e) {
							thenException.add(e);
						}
					};
					Thread executeThread = new Thread(runTask);
					executeThread.start();
				}
				
				
				
				// ---- check the current doen execption ---
				if (!thenException.isEmpty()) {
					
					Handler<Throwable> throwableHandler =  activityUnits[i].getThenFailHandler();
					if (null != throwableHandler) {
						
						Runnable runTask = () -> {
							try {
								throwableHandler.handle(thenException.iterator().next());
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {

							}

						};
						
						Thread executeThread = new Thread(runTask);
						executeThread.start();						
					}
					
					throw new Exception("Stop for exception");
					

				}				
				
			}
			

			
			if ( !runningThrowables.isEmpty() ) {
				
				StringBuilder allExceptionMsg = new StringBuilder();

				for (int k = 0 ; k < runningThrowables.size() ; k++) {
					
					allExceptionMsg.append( runningThrowables.get(k).getLocalizedMessage() );
					allExceptionMsg.append("\n");
					
				}
				
				Exception ex = new Exception(allExceptionMsg.toString());
				
				Handler<Throwable>[] failHandlers = failHandlerColl.toArray(new Handler[0]);
				if (null != failHandlers) {

					
					
					
					CountDownLatch failSignal = new CountDownLatch(
							failHandlers.length);

					for (int j = 0; j < failHandlers.length; j++) {

						Handler<Throwable> handlerEvent = failHandlers[j];

						Runnable runTask = () -> {
							try {
								handlerEvent.handle(ex);
							} catch (Throwable e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} finally {
								failSignal.countDown();
							}

						};

						Thread executeThread = new Thread(runTask);
						executeThread.start();

					}

					failSignal.await();

				}				
				
				throw new Exception("error existed.");
			}
			

			// --- call done handle ---
			Handler<?>[] doneHandlers = doneHandlerColl.toArray(new Handler[0]);
			if (null != doneHandlers) {
				CountDownLatch doneSignal = new CountDownLatch(
						doneHandlers.length);

				for (int j = 0; j < doneHandlers.length; j++) {

					Handler<?> handlerEvent = doneHandlers[j];

					Runnable runTask = () -> {
						try {
							handlerEvent.handle(null);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							doneSignal.countDown();
						}

					};

					Thread executeThread = new Thread(runTask);
					executeThread.start();

				}

				doneSignal.await();
			}

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("Promise has been handled.");
			}
		}

	}

	protected List<Handler<P>> getProgressCallbacks() {
		return this.progressCallbacks;
	}

	protected List<Handler<D>> getDoneCallbacks() {
		return this.doneCallbacks;
	}

	protected List<Handler<Throwable>> getFailCallbacks() {
		return this.failCallbacks;
	}

}
