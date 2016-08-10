/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.vsg.cusp.concurrent.AsyncResult;
import org.vsg.cusp.concurrent.Callback;
import org.vsg.cusp.concurrent.Handler;
import org.vsg.cusp.event.DeliveryOptions;
import org.vsg.cusp.event.EventMethodDescription;
import org.vsg.cusp.event.EventMethodRegister;
import org.vsg.cusp.event.EventTrigger;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageBus;
import org.vsg.cusp.event.RuntimeParams;

/**
 * @author ruanweibiao
 *
 */
public class EventTriggerImpl implements EventTrigger {
	
	private EventMethodRegister eventMethodRegister;
	
	private MessageBus messageBus;
	
	@Inject
	public EventTriggerImpl(
			EventMethodRegister eventMethodRegister , @Named("EventMethodMessageBus") MessageBus messageBus) {
		this.eventMethodRegister = eventMethodRegister;
		this.messageBus = messageBus;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.EventTrigger#fire(java.lang.String, org.vsg.cusp.event.RuntimeParams, org.vsg.cusp.concurrent.Callback)
	 */
	@Override
	public <T> void fire(String eventName, RuntimeParams params,
			Callback<T> callback) throws Exception {
		
		// --- call method for event name ---
		Set<EventMethodDescription>   eventMethodDescSet =  eventMethodRegister.findAllRegisterEventsByName(eventName);

		// --- save state and create new thread to monitor call back handle
		if (eventMethodDescSet.isEmpty()) {
			throw new Exception("Could not find the registerd event method. ");
		}
		
		/**
		 * sent the method content to mq server
		 * 
		 * create event transation id for every message and construct the message wrapper event handle 
		 * 
		 */
		DeliveryOptions deliveryOpts = new DeliveryOptions();
		deliveryOpts.addHeader("basekey", "123");
		deliveryOpts.setCodecName( "evtmethod-codec" );
		
		Handler<AsyncResult<Message<T>>> callbackHandler = new Handler<AsyncResult<Message<T>>>() {

			@Override
			public void handle(AsyncResult<Message<T>> event) {
				// TODO Auto-generated method stub
				System.out.println("call back event");
			}
			
		};
		
		
		messageBus.send("hello message", "string", deliveryOpts, callbackHandler);
	}

}
