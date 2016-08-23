/**
 * 
 */
package org.vsg.deferred.impl;

import org.vsg.deferred.Callback;

/**
 * @author ruanweibiao
 *
 */
public class ActivityUnit {
	
	private Callback<?>[] processCallbacks;
	
	private short sequenceOrder = 0;
	
	private Callback<?>[] doneCallbacks;
	
	private Callback<Throwable>[] failCallbacks;
	
	private Callback<?> thenDoneCallback;
	
	private Callback<Throwable> thenFailCallback;

	public Callback<?>[] getProcessCallbacks() {
		return processCallbacks;
	}

	public void setProcessCallbacks(Callback<?>[] processCallbacks) {
		this.processCallbacks = processCallbacks;
	}

	public short getSequenceOrder() {
		return sequenceOrder;
	}




	public void setSequenceOrder(short sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}




	public Callback<?>[] getDoneCallbacks() {
		return doneCallbacks;
	}




	public void setDoneCallbacks(Callback<?>[] doneCallbacks) {
		this.doneCallbacks = doneCallbacks;
	}




	public Callback<Throwable>[] getFailCallbacks() {
		return failCallbacks;
	}




	public void setFailCallbacks(Callback<Throwable>[] failCallbacks) {
		this.failCallbacks = failCallbacks;
	}




	public Callback<?> getThenDoneCallback() {
		return thenDoneCallback;
	}




	public void setThenDoneCallback(Callback<?> thenDoneCallback) {
		this.thenDoneCallback = thenDoneCallback;
	}




	public Callback<Throwable> getThenFailCallback() {
		return thenFailCallback;
	}




	public void setThenFailCallback(Callback<Throwable> thenFailCallback) {
		this.thenFailCallback = thenFailCallback;
	}




	@Override
	public String toString() {
		return "ActivityUnit [processCallbacks=" + processCallbacks
				+ ", sequenceOrder=" + sequenceOrder + ", doneCallbacks="
				+ doneCallbacks + ", failCallbacks=" + failCallbacks
				+ ", thenDoneCallback=" + thenDoneCallback + ", thenFailCallback="
				+ thenFailCallback + "]";
	}
	
	

}
