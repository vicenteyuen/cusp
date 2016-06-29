package org.vsg.cusp.eventbus.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.CaseInsensitiveHeaders;
import org.vsg.cusp.eventbus.DeliveryOptions;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.Message;
import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.MultiMap;

public class MessageImpl<U, T> implements Message<T> {

	private static Logger log = LoggerFactory.getLogger(MessageImpl.class);

	private static byte WIRE_PROTOCOL_VERSION = 1;

	private String address;

	private MessageCodec<U, T> messageCodec;

	private U sentBody;

	private T receivedBody;

	private boolean send;

	private int bodyPos;

	private int headersPos;

	private EventBusImpl bus;

	private String replyAddress;
	
	
	
	 public EventBusImpl getBus() {
		return bus;
	}


	public void setBus(EventBusImpl bus) {
		this.bus = bus;
	}


	public boolean send() {
		    return send;
		  }	
	

	public String getReplyAddress() {
		return replyAddress;
	}

	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}

	@Override
	public String address() {
		// TODO Auto-generated method stub
		return address;
	}

	private MultiMap headers;

	@Override
	public MultiMap headers() {
		if (headers == null) {
			// decodeHeaders();
		}
		if (headers == null) {
			headers = new CaseInsensitiveHeaders();
		}
		return headers;
	}

	@Override
	public T body() {
		// TODO Auto-generated method stub
		if (null == this.receivedBody && bodyPos != 0) {
			decodeBody();
		}
		return this.receivedBody;
	}

	private void decodeBody() {
		// this.receivedBody = messageCodec;
	}

	@Override
	public String replyAddress() {
		// TODO Auto-generated method stub
		return replyAddress;
	}

	@Override
	public void reply(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public <R> void reply(Object message,
			Handler<AsyncResult<Message<R>>> replyHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reply(Object message, DeliveryOptions options) {
		// TODO Auto-generated method stub

	}

	@Override
	public <R> void reply(Object message, DeliveryOptions options,
			Handler<AsyncResult<Message<R>>> replyHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(int failureCode, String message) {
		// TODO Auto-generated method stub

	}

}
