/**
 * 
 */
package org.vsg.deferred.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.vsg.cusp.core.Handler;

/**
 * @author ruanweibiao
 *
 */
public class ActivityUnit {
	
	private Handler<?>[] processHandlers;
	
	private short sequenceOrder = 0;
	
	private Handler<?>[] doneHandlers;
	
	private Handler<Throwable>[] failHandlers;
	
	private Handler<?> thenDoneHandler;
	
	private Handler<Throwable> thenFailHandler;

	public Handler<?>[] getProcessHandlers() {
		return processHandlers;
	}

	public void setProcessHandlers(Handler<?>[] processHandlers) {
		this.processHandlers = processHandlers;
	}

	public short getSequenceOrder() {
		return sequenceOrder;
	}




	public void setSequenceOrder(short sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}




	public Handler<?>[] getDoneHandlers() {
		return doneHandlers;
	}




	public void setDoneHandlers(Handler<?>[] doneHandlers) {
		this.doneHandlers = doneHandlers;
	}




	public Handler<Throwable>[] getFailHandlers() {
		return failHandlers;
	}




	public void setFailHandlers(Handler<Throwable>[] failHandlers) {
		this.failHandlers = failHandlers;
	}




	public Handler<?> getThenDoneHandler() {
		return thenDoneHandler;
	}




	public void setThenDoneHandler(Handler<?> thenDoneHandler) {
		this.thenDoneHandler = thenDoneHandler;
	}




	public Handler<Throwable> getThenFailHandler() {
		return thenFailHandler;
	}




	public void setThenFailHandler(Handler<Throwable> thenFailHandler) {
		this.thenFailHandler = thenFailHandler;
	}




	@Override
	public String toString() {
		return "ActivityUnit [processHandlers=" + processHandlers
				+ ", sequenceOrder=" + sequenceOrder + ", doneHandlers="
				+ doneHandlers + ", failHandlers=" + failHandlers
				+ ", thenDoneHandler=" + thenDoneHandler + ", thenFailHandler="
				+ thenFailHandler + "]";
	}
	
	

}
