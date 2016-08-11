/**
 * 
 */
package org.vsg.cusp.eventbus.impl;

import org.vsg.cusp.concurrent.AsyncResult;
import org.vsg.cusp.concurrent.Handler;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.eventbus.MessageConsumer;

/**
 * @author vison
 *
 */
public class MessageConsumerImpl<T> implements MessageConsumer<T> {

	@Override
	public MessageConsumer<T> exceptionHandler(Handler<Throwable> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer<T> handler(Handler<Message<T>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer<T> pause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer<T> resume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer<T> endHandler(Handler<Void> endHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRegistered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String address() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer<T> setMaxBufferedMessages(int maxBufferedMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxBufferedMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void completionHandler(Handler<AsyncResult<Void>> completionHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(Handler<AsyncResult<Void>> completionHandler) {
		// TODO Auto-generated method stub
		
	}

}
