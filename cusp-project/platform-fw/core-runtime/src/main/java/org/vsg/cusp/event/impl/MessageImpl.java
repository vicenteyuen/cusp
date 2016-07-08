package org.vsg.cusp.event.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.MessageCodecSupport;
import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.CaseInsensitiveHeaders;
import org.vsg.cusp.eventbus.DeliveryOptions;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.MultiMap;

public class MessageImpl<U, T> implements Message<T> , MessageCodecSupport {

	private static Logger log = LoggerFactory.getLogger(MessageImpl.class);

	private static byte WIRE_PROTOCOL_VERSION = 1;

	private String address;

	private MessageCodec<U, T> messageCodec;

	private U sentBody;

	private T receivedBody;

	private boolean send;

	private int bodyPos;

	private int headersPos;

	private ZmqEventBusImplEndPoint bus;

	private String replyAddress;

	public MessageImpl() {

	}

	public MessageImpl(String address, U sentBody,
			MessageCodec<U, T> messageCodec, boolean send, ZmqEventBusImplEndPoint bus) {
		this.address = address;
		this.sentBody = sentBody;
		this.messageCodec = messageCodec;
		this.send = send;
		this.bus = bus;
	}

	public ZmqEventBusImplEndPoint getBus() {
		return bus;
	}

	public void setBus(ZmqEventBusImplEndPoint bus) {
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
		return address;
	}

	
	
	String getAddress() {
		return address;
	}

	void setAddress(String address) {
		this.address = address;
	}

	U getSentBody() {
		return sentBody;
	}

	void setSentBody(U sentBody) {
		this.sentBody = sentBody;
	}

	T getReceivedBody() {
		return receivedBody;
	}

	void setReceivedBody(T receivedBody) {
		this.receivedBody = receivedBody;
	}

	boolean isSend() {
		return send;
	}

	void setSend(boolean send) {
		this.send = send;
	}

	int getBodyPos() {
		return bodyPos;
	}

	void setBodyPos(int bodyPos) {
		this.bodyPos = bodyPos;
	}

	int getHeadersPos() {
		return headersPos;
	}

	void setHeadersPos(int headersPos) {
		this.headersPos = headersPos;
	}

	MultiMap getHeaders() {
		return headers;
	}

	void setHeaders(MultiMap headers) {
		this.headers = headers;
	}

	void setMessageCodec(MessageCodec<U, T> messageCodec) {
		this.messageCodec = messageCodec;
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
		if (receivedBody == null && sentBody != null) {
			receivedBody = messageCodec.transform(sentBody);
		}
		return receivedBody;
	}


	@Override
	public String replyAddress() {

		return replyAddress;
	}

	@Override
	public void reply(Object message) {
		reply(message, new DeliveryOptions(), null);
	}

	@Override
	public <R> void reply(Object message,
			Handler<AsyncResult<Message<R>>> replyHandler) {
		reply(message, new DeliveryOptions(), replyHandler);
	}

	@Override
	public void reply(Object message, DeliveryOptions options) {
		reply(message, options, null);
	}

	@Override
	public <R> void reply(Object message, DeliveryOptions options,
			Handler<AsyncResult<Message<R>>> replyHandler) {
		if (replyAddress != null) {
			sendReply(bus.createMessage(true, replyAddress,
					options.getHeaders(), message, options.getCodecName()),
					options, replyHandler);
		}
	}

	protected <R> void sendReply(MessageImpl msg, DeliveryOptions options,
			Handler<AsyncResult<Message<R>>> replyHandler) {
		if (bus != null) {
			bus.sendReply(msg, this, options, replyHandler);
		}
	}
	
	@Override
	public MessageCodec getMessageCodec() {
		return this.messageCodec;
	}

	@Override
	public void fail(int failureCode, String message) {
		// TODO Auto-generated method stub

	}

}
