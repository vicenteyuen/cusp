/**
 * 
 */
package org.vsg.cusp.concurrent.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.vsg.cusp.concurrent.OperationEvent;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventImpl implements OperationEvent {
	
	private String eventId;
	
	private Serializable[] arguments;
	
	private Method bindMethod;

	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.OperationEvent#getEventId()
	 */
	@Override
	public String getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}


	@Override
	public void setRuntimeArgument(Serializable[] arguments) {
		/**
		 * check arguments object type and support arugment counter.
		 */
		this.arguments = arguments;
	}

	@Override
	public Serializable[] getRuntimeArgument() {
		// TODO Auto-generated method stub
		return this.arguments;
	}


	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.OperationEvent#assobindMethod()
	 */
	@Override
	public Method assoBindMethod() {
		// TODO Auto-generated method stub
		return bindMethod;
	}



	public Method getBindMethod() {
		return bindMethod;
	}

	public void setBindMethod(Method bindMethod) {
		this.bindMethod = bindMethod;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	private String className;

	@Override
	public String assoClassName() {
		// TODO Auto-generated method stub
		return className;
	}
	
	
	public void setAssoClassName(String className) {
		this.className = className;
	}
	
	

}
