package org.vsg.cusp.eventbus.impl;

import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.MessageConsumer;
import org.zeromq.ZMQ.Socket;

public class MessageConsumerImpl implements MessageConsumer {
	
	private Socket clientSocket;
	
	public MessageConsumerImpl(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	

	@Override
	public MessageConsumer exceptionHandler(Handler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer handler(Handler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer pause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer resume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer endHandler(Handler endHandler) {
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
	public MessageConsumer setMaxBufferedMessages(int maxBufferedMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxBufferedMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void completionHandler(Handler completionHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(Handler completionHandler) {
		// TODO Auto-generated method stub

	}

}
