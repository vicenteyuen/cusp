/**
 * 
 */
package org.vsg.cusp.concurrent.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.vsg.cusp.concurrent.OperationEvent;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventImpl implements OperationEvent {
	
	private String eventId;
	
	private Map<String , Object> arguments;
	
	private Method bindMethod;

	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.OperationEvent#getEventId()
	 */
	@Override
	public String getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.OperationEvent#injectRunningtimeArgument(java.util.Map)
	 */
	@Override
	public void injectRunningtimeArgument(Map<String, Object> arguements) {
		// TODO Auto-generated method stub
		this.arguments = arguments;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.OperationEvent#assobindMethod()
	 */
	@Override
	public Method assoBindMethod() {
		// TODO Auto-generated method stub
		return bindMethod;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
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
