package org.vsg.cusp.concurrent;

import java.util.Map;

import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.Handler;

public interface EventFlow<T> {
	
	
	<T> void fire(String eventName , Map<String , Object> fireArguements , Handler<AsyncResult<T>>...handlers);
	
	/**
	 * fire event when the event flow is end handle
	 * @param handle
	 */
	void fireAtEnd(Handler<AsyncResult> handle);
	
	public static final byte MODE_LOCAL = 0;
	
	public static final byte MODE_DIS = 1;
	
	Promise promise(byte mode);
	
	// start deffer event 
	void startDeffer();

}
