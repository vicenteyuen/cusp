/**
 * 
 */
package org.vsg.cusp.event;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Vector;

/**
 * @author Vicente Yuen
 *
 */
public class EventMethodDescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7322828122531471379L;
	
	
	private String eventName;
	
	private String clzName;
	
	private String methodName;
	
	private String[] methodParamsType;
	
	
	
	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getClzName() {
		return clzName;
	}


	public void setClzName(String clzName) {
		this.clzName = clzName;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String[] getMethodParamsType() {
		return methodParamsType;
	}


	public void setMethodParamsType(String[] methodParamsType) {
		this.methodParamsType = methodParamsType;
	}


	public static Method getssoMethod(EventMethodDescription desc) {
		return null;
	}
	
	
	public static EventMethodDescription getMethodDescription(String eventName , Class clzName , Method method) {
		EventMethodDescription desc = new EventMethodDescription();
		
		Objects.requireNonNull( eventName , "Event name is not null.");
		
		desc.setEventName(eventName);
		desc.setClzName( clzName.getName() );
		
		desc.setMethodName( method.getName() );
		
		Parameter[] params = method.getParameters();
		
		Vector<String> paramClzVec = new Vector<String>();
		for (int i = 0 ; i< params.length ; i++) {
			Parameter  param =  params[i];
			paramClzVec.add( param.getClass().getName() );
		}
		
		desc.setMethodParamsType( paramClzVec.toArray(new String[0]) );
		
		
		return desc;
	}
	
	
	
	
	
	

}
