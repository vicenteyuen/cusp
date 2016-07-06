package org.vsg.cusp.concurrent;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 
 * @author Vicente Yuen
 *
 */
public interface OperationEvent {
	
	/**
	 * return binding event id for method
	 * @return
	 */
	String getEventId();
	
	// ---- associa method ---
	void setRuntimeArgument(Serializable[] arguments);
	
	Serializable[] getRuntimeArgument();
	
	Method assoBindMethod();
	
	String assoClassName();

}
