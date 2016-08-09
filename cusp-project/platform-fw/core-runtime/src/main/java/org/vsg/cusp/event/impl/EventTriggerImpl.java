/**
 * 
 */
package org.vsg.cusp.event.impl;

import javax.inject.Inject;

import org.vsg.cusp.concurrent.Callback;
import org.vsg.cusp.event.EventMethodRegister;
import org.vsg.cusp.event.EventTrigger;
import org.vsg.cusp.event.RuntimeParams;

/**
 * @author ruanweibiao
 *
 */
public class EventTriggerImpl implements EventTrigger {
	
	private EventMethodRegister eventMethodRegister;
	
	@Inject
	public EventTriggerImpl(EventMethodRegister eventMethodRegister) {
		this.eventMethodRegister = eventMethodRegister;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.EventTrigger#fire(java.lang.String, org.vsg.cusp.event.RuntimeParams, org.vsg.cusp.concurrent.Callback)
	 */
	@Override
	public <T> void fire(String eventName, RuntimeParams params,
			Callback<T> callback) {
		
		// --- call method for event name ---
		
		System.out.println("event Name : " + eventName);
		// --- save state and create new thread to monitor call back handle
		

	}

}
