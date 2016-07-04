package org.vsg.cusp.concurrent;

import java.lang.reflect.Method;
import java.util.Map;

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
	void injectRunningtimeArgument(Map<String,Object> arguements);
	
	
	Method assobindMethod();

}
