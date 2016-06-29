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

	public MessageImpl() {

	}

	public MessageImpl(String address, U sentBody,
			MessageCodec<U, T> messageCodec, boolean send, EventBusImpl bus) {
		this.address = address;
		this.sentBody = sentBody;
		this.messageCodec = messageCodec;
		this.send = send;
		this.bus = bus;
	}

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
	public void fail(int failureCode, String message) {
		// TODO Auto-generated method stub

	}

}
