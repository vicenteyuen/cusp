package org.vsg.deferred.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.deferred.Callback;
import org.vsg.deferred.Promise;

public abstract class AbstractPromise<D, F extends Throwable, P> implements
		Promise<D, F, P> {

	protected Logger logger = LoggerFactory.getLogger(AbstractPromise.class);

	protected volatile State state = State.PENDING;

	protected D resolveResult;

	protected F rejectResult;

	private List<org.vsg.deferred.Callback<D>> doneCallbacks = new CopyOnWriteArrayList<Callback<D>>();
	private List<Callback<Throwable>> failCallbacks = new CopyOnWriteArrayList<Callback<Throwable>>();
	private List<Callback<P>> progressCallbacks = new CopyOnWriteArrayList<Callback<P>>();

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
	public Promise<D, F, P> then(Callback<P> Callback) {
		// --- set commit ---

		return this;
	}

	@Override
	public Promise<D, F, P> succeed(Callback<D> callback) {
		doneCallbacks.add(callback);
		return this;
	}

	private void triggerSucceed(Callback<D> callback, D resolved) {
		// callback.handle( resolved );
	}

	@Override
	public Promise<D, F, P> fail(Callback<F> errorCallback) {
		failCallbacks.add((Callback) errorCallback);

		return this;
	}

	protected void triggerFail(Callback<F> callback, F rejected) {
		// callback.handle(rejected);
	}

	@Override
	public Promise<D, F, P> progress(Callback<P> callback) {
		progressCallbacks.add(callback);
		return this;
	}

	private void triggerProgress(Callback<P> callback, P progress) {
		// callback.handle(progress);
	}

	@Override
	public Promise<D, F, P> then(Callback<P> Callback, Callback<D> succeedCallback,
			Callback<F> failCallback) {

		this.setCommited(true);

		this.progressCallbacks.add(Callback);
		// --- create activityUnit ----
		notifyActivityUnitCollection((Callback<?>) succeedCallback,
				(Callback<Throwable>) failCallback);

		this.setCommited(false);

		return this;
	}

	private short seqNo = 0;

	/**
	 * nodify to create activity unit collection
	 */
	private void notifyActivityUnitCollection(Callback<?> doneCallback,
			Callback<Throwable> failCallback) {
		ActivityUnit activityUnit = new ActivityUnit();
		activityUnit.setSequenceOrder(seqNo++);
		/**
		 * copy progress call back
		 */
		if (!this.progressCallbacks.isEmpty()) {

			activityUnit.setProcessCallbacks(this.progressCallbacks
					.toArray(new Callback[0]));
		}

		activityUnit.setThenDoneCallback(doneCallback);
		activityUnit.setThenFailCallback(failCallback);

		if (end) {

			if (!this.doneCallbacks.isEmpty()) {
				activityUnit.setDoneCallbacks(this.doneCallbacks
						.toArray(new Callback[0]));
			}

			if (!this.failCallbacks.isEmpty()) {
				activityUnit.setFailCallbacks(this.failCallbacks
						.toArray(new Callback[0]));
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
		 * calllbackCounter >= 0 ; calllbackCounter--) { Callback<P> CallbackEvent
		 * = progressCallbacks.get(calllbackCounter);
		 * 
		 * Runnable runTask = () -> { CallbackEvent.handle(null);
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
		 * 0 ; calllbackCounter--) { Callback<D> CallbackEvent =
		 * doneCallbacks.get(calllbackCounter);
		 * 
		 * Runnable runTask = () -> { CallbackEvent.handle(null); };
		 * 
		 * Thread executeThread = new Thread(runTask); executeThread.start(); }
		 */

		return this;
	}

	private void doRun() {

		ActivityUnit[] activityUnits = this.scheActivityUnits
				.toArray(new ActivityUnit[0]);

		Collection<Callback<?>> doneCallbackColl = new Vector<Callback<?>>();
		Collection<Callback<Throwable>> failCallbackColl = new Vector<Callback<Throwable>>();

		List<Throwable> runningThrowables = new ArrayList<Throwable>();

		/**
		 * pre arrange activity unit handle
		 */
		for (int i = 0; i < activityUnits.length; i++) {
			Callback<?>[] doneCallbacks = activityUnits[i].getDoneCallbacks();
			Callback<Throwable>[] failCallbacks = activityUnits[i]
					.getFailCallbacks();

			if (null != doneCallbacks) {
				for (int j = 0; j < doneCallbacks.length; j++) {
					doneCallbackColl.add(doneCallbacks[j]);
				}
			}

			if (null != failCallbacks) {
				for (int j = 0; j < failCallbacks.length; j++) {
					failCallbackColl.add(failCallbacks[j]);
				}
			}
		}

		try {
			
			for (int i = 0; i < activityUnits.length; i++) {
				List<Throwable> thenException = new Vector<Throwable>();
				
				Callback<?>[] processCallbacks = activityUnits[i].getProcessCallbacks();

				/**
				 * process handle
				 */
				if (null != processCallbacks) {

					CountDownLatch procSignal = new CountDownLatch(
							processCallbacks.length);

					for (int j = 0; j < processCallbacks.length; j++) {

						Callback<?> CallbackEvent = processCallbacks[j];

						Runnable runTask = () -> {
							try {
								CallbackEvent.handle(null);
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
				if (null != activityUnits[i].getThenDoneCallback()) {
					Callback<?> doneCallback = activityUnits[i]
							.getThenDoneCallback();
					Runnable runTask = () -> {
						try {
							doneCallback.handle(null);
						} catch (Throwable e) {
							thenException.add(e);
						}
					};
					Thread executeThread = new Thread(runTask);
					executeThread.start();
				}
				
				
				
				// ---- check the current doen execption ---
				if (!thenException.isEmpty()) {
					
					Callback<Throwable> throwableCallback =  activityUnits[i].getThenFailCallback();
					if (null != throwableCallback) {
						
						Runnable runTask = () -> {
							try {
								throwableCallback.handle(thenException.iterator().next());
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
				
				Callback<Throwable>[] failCallbacks = failCallbackColl.toArray(new Callback[0]);
				if (null != failCallbacks) {

					
					
					
					CountDownLatch failSignal = new CountDownLatch(
							failCallbacks.length);

					for (int j = 0; j < failCallbacks.length; j++) {

						Callback<Throwable> CallbackEvent = failCallbacks[j];

						Runnable runTask = () -> {
							try {
								CallbackEvent.handle(ex);
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
			Callback<?>[] doneCallbacks = doneCallbackColl.toArray(new Callback[0]);
			if (null != doneCallbacks) {
				CountDownLatch doneSignal = new CountDownLatch(
						doneCallbacks.length);

				for (int j = 0; j < doneCallbacks.length; j++) {

					Callback<?> CallbackEvent = doneCallbacks[j];

					Runnable runTask = () -> {
						try {
							CallbackEvent.handle(null);
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

	protected List<Callback<P>> getProgressCallbacks() {
		return this.progressCallbacks;
	}

	protected List<Callback<D>> getDoneCallbacks() {
		return this.doneCallbacks;
	}

	protected List<Callback<Throwable>> getFailCallbacks() {
		return this.failCallbacks;
	}

}
