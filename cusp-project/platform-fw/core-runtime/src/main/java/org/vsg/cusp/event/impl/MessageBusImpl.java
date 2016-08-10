/**
 * 
 */
package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.AsyncResult;
import org.vsg.cusp.event.DeliveryOptions;
import org.vsg.cusp.event.Handler;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageBus;
import org.vsg.cusp.eventbus.MessageConsumer;

/**
 * @author Vicente Yuen
 *
 */
public class MessageBusImpl implements MessageBus {

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.MessageBus#send(java.lang.String, java.lang.Object, org.vsg.cusp.event.DeliveryOptions, org.vsg.cusp.event.Handler)
	 */
	@Override
	public <T> MessageBus send(String address, Object message,
			DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		
		System.out.println("address : " + address);

		return this;
	}

	@Override
	public <T> MessageConsumer<T> consumer(String address) {
		

		return null;
	}
	
	
	

}
