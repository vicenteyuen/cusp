package org.vsg.cusp.eventbus.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.DeliveryOptions;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.Message;
import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.MessageConsumer;
import org.vsg.cusp.eventbus.MessageProducer;
import org.vsg.cusp.eventbus.MultiMap;
import org.vsg.cusp.eventbus.SendContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class EventBusImpl implements EventBus {

	private static Logger logger = LoggerFactory.getLogger(EventBusImpl.class);

	protected volatile boolean started;

	protected ConcurrentMap<String, Handlers> handlerMap = new ConcurrentHashMap<>();

	protected CodecManager codecManager = new CodecManager();

	private EventBusOptions options;

	public EventBusImpl(EventBusOptions options) {
		// this.zmqContext = zmqContext;

		this.options = options;

		init();
	}


	private void init() {

	}

	@Override
	public EventBus send(String address, Object message) {
		return send(address, message, new DeliveryOptions(), null);
	}

	@Override
	public <T> EventBus send(String address, Object message,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		return send(address, message, new DeliveryOptions(), replyHandler);
	}

	@Override
	public EventBus send(String address, Object message, DeliveryOptions options) {
		return send(address, message, options, null);
	}

	@Override
	public <T> EventBus send(String address, Object message,
			DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		sendOrPubInternal(
				createMessage(true, address, options.getHeaders(), message,
						options.getCodecName()), options, replyHandler);
		return this;
	}

	@Override
	public EventBus publish(String address, Object message) {

		return publish(address, message, new DeliveryOptions());
	}

	@Override
	public EventBus publish(String address, Object message,
			DeliveryOptions options) {
		sendOrPubInternal(
				createMessage(false, address, options.getHeaders(), message,
						options.getCodecName()), options, null);
		return this;
	}

	protected MessageImpl createMessage(boolean send, String address,
			MultiMap headers, Object body, String codecName) {
		Objects.requireNonNull(address, "no null address accepted");
		MessageCodec codec = codecManager.lookupCodec(body, codecName);
		MessageImpl msg = new MessageImpl(address, body, codec, true, this);
		return msg;
	}

	private <T> void sendOrPubInternal(MessageImpl message,
			DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		checkStarted();
		HandlerRegistration<T> replyHandlerRegistration = createReplyHandlerRegistration(
				message, options, replyHandler);
		SendContextImpl<T> sendContext = new SendContextImpl<>(message,
				options, replyHandlerRegistration);
		sendContext.next();

	}

	private final AtomicLong replySequence = new AtomicLong(0);

	protected String generateReplyAddress() {
		return Long.toString(replySequence.incrementAndGet());
	}

	protected <T> Handler<Message<T>> convertHandler(
			Handler<AsyncResult<Message<T>>> handler) {
		return reply -> {
			Future<Message<T>> result;
			/*
			 * if (reply.body() instanceof ReplyException) { // This is kind of
			 * clunky - but hey-ho ReplyException exception = (ReplyException)
			 * reply.body(); metrics.replyFailure(reply.address(),
			 * exception.failureType()); result =
			 * Future.failedFuture(exception); } else { result =
			 * Future.succeededFuture(reply); }
			 */
			// handler.handle(result);
		};
	}

	private <T> HandlerRegistration<T> createReplyHandlerRegistration(
			MessageImpl message, DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		if (replyHandler != null) {
			long timeout = options.getSendTimeout();
			String replyAddress = generateReplyAddress();
			message.setReplyAddress(replyAddress);
			Handler<Message<T>> simpleReplyHandler = convertHandler(replyHandler);

			Context context = ZMQ.context(1);
			Socket requester = context.socket(ZMQ.REQ);
			requester.connect("tcp://localhost:5559");

			HandlerRegistration<T> registration = new HandlerRegistration<>(
					requester);
			registration.handler(simpleReplyHandler);
			return registration;
		} else {
			return null;
		}

	}

	@Override
	public <T> MessageConsumer<T> consumer(String address) {
		// --- create consumer ---
		Context context = ZMQ.context(1);
		Socket requester = context.socket(ZMQ.REQ);
		requester.connect("tcp://localhost:5559");

		Objects.requireNonNull(address, "address");
		HandlerRegistration mci = new HandlerRegistration(requester);
		return mci;
	}

	@Override
	public <T> MessageConsumer<T> consumer(String address,
			Handler<Message<T>> handler) {
		Objects.requireNonNull(handler, "handler");
		MessageConsumer<T> consumer = consumer(address);
		return consumer;
	}

	@Override
	public <T> MessageConsumer<T> localConsumer(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageConsumer<T> localConsumer(String address,
			Handler<Message<T>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageProducer<T> sender(String address) {
		Objects.requireNonNull(address, "address");
		MessageProducerImpl ppi = new MessageProducerImpl<>(address, true,
				new DeliveryOptions());
		return ppi;
	}

	@Override
	public <T> MessageProducer<T> sender(String address, DeliveryOptions options) {
		Objects.requireNonNull(address, "address");
		Objects.requireNonNull(options, "options");

		MessageProducerImpl ppi = new MessageProducerImpl<>(address, true,
				options);
		return ppi;
	}

	@Override
	public <T> MessageProducer<T> publisher(String address) {
		Objects.requireNonNull(address, "address");
		MessageProducerImpl ppi = new MessageProducerImpl<>(address, true,
				new DeliveryOptions());
		return ppi;
	}

	@Override
	public <T> MessageProducer<T> publisher(String address,
			DeliveryOptions options) {
		Objects.requireNonNull(address, "address");
		Objects.requireNonNull(options, "options");
		return new MessageProducerImpl<>(address, false, options);
	}

	@Override
	public EventBus registerCodec(MessageCodec codec) {
		codecManager.registerCodec(codec);
		return this;
	}

	@Override
	public EventBus unregisterCodec(String name) {
		codecManager.unregisterCodec(name);
		return this;
	}

	@Override
	public <T> EventBus registerDefaultCodec(Class<T> clazz,
			MessageCodec<T, ?> codec) {
		codecManager.registerDefaultCodec(clazz, codec);
		return this;
	}

	@Override
	public EventBus unregisterDefaultCodec(Class clazz) {
		codecManager.unregisterDefaultCodec(clazz);
		return this;
	}

	protected void checkStarted() {
		if (!started) {
			throw new IllegalStateException("Event Bus is not started");
		}
	}

	@Override
	public void start(Handler<AsyncResult<Void>> completionHandler) {
		if (started) {
			throw new IllegalStateException("Already started");
		}

		started = true;

		// completionHandler.handle(Future.succeededFuture());
	}

	@Override
	public void close(Handler<AsyncResult<Void>> completionHandler) {
		checkStarted();
		unregisterAll();

		if (completionHandler != null) {
			// vertx.runOnContext(v ->
			// completionHandler.handle(Future.succeededFuture()));
		}

	}

	private void unregisterAll() {
		// Unregister all handlers explicitly - don't rely on context hooks
		for (Handlers handlers : handlerMap.values()) {
			for (HandlerHolder holder : handlers.list) {
				holder.getHandler().unregister(true);
			}
		}
	}

	private List<Handler<SendContext>> interceptors = new CopyOnWriteArrayList<>();

	/**
	 * Define base inner context
	 * 
	 * @author Vicente Yuen
	 *
	 * @param <T>
	 */
	protected class SendContextImpl<T> implements SendContext<T> {

		public MessageImpl message;
		public DeliveryOptions options;
		public HandlerRegistration<T> handlerRegistration;
		public Iterator<Handler<SendContext>> iter;

		public SendContextImpl(MessageImpl message, DeliveryOptions options,
				HandlerRegistration<T> handlerRegistration) {
			this.message = message;
			this.options = options;
			this.handlerRegistration = handlerRegistration;
			this.iter = interceptors.iterator();
		}

		@Override
		public Message<T> message() {
			return message;
		}

		@Override
		public void next() {

			if (iter.hasNext()) {
				Handler<SendContext> handler = iter.next();
				try {
					handler.handle(this);
				} catch (Throwable t) {
					logger.error("Failure in interceptor", t);
				}
			} else {
				sendOrPub(this);
			}
		}

		@Override
		public boolean send() {
			return message.send();
		}
	}

	protected <T> void sendOrPub(SendContextImpl<T> sendContext) {
		MessageImpl message = sendContext.message;

		
		Context clientContext = ZMQ.context(zmq.ZMQ.ZMQ_IO_THREADS);
		// Socket to talk to server
		Socket requester = clientContext.socket(ZMQ.REQ);

		StringBuilder connProtocol = new StringBuilder("tcp://");
		connProtocol.append("localhost");
		connProtocol.append(":").append(this.options.getBrokerPort());
		
		System.out.println(connProtocol.toString());

		requester.connect(connProtocol.toString());

		
		// --- define sent content ,handle 
		requester.send(message.body().toString(), 0);

		requester.close();
		clientContext.term();

		// metrics.messageSent(message.address(), !message.send(), true, false);
		deliverMessageLocally(sendContext);
	}

	protected <T> void deliverMessageLocally(SendContextImpl<T> sendContext) {
		if (!deliverMessageLocally(sendContext.message)) {
			// no handlers
			/*
			 * metrics.replyFailure(sendContext.message.address,
			 * ReplyFailure.NO_HANDLERS); if (sendContext.handlerRegistration !=
			 * null) {
			 * sendContext.handlerRegistration.sendAsyncResultFailure(ReplyFailure
			 * .NO_HANDLERS, "No handlers for address " +
			 * sendContext.message.address); }
			 */
		}
	}

	protected <T> void sendReply(MessageImpl replyMessage,
			MessageImpl replierMessage, DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		if (replyMessage.address() == null) {
			throw new IllegalStateException("address not specified");
		} else {
			HandlerRegistration<T> replyHandlerRegistration = createReplyHandlerRegistration(replyMessage, options, replyHandler);
			
			//new ReplySendContextImpl<>(replyMessage, options,replyHandlerRegistration, replierMessage).next();
		}
	}

	protected <T> boolean deliverMessageLocally(MessageImpl msg) {
		msg.setBus(this);
		Handlers handlers = handlerMap.get(msg.address());

		if (handlers != null) {
			if (msg.send()) {
				// Choose one
				HandlerHolder holder = handlers.choose();
				/*
				 * if (holder != null) { metrics.messageReceived(msg.address(),
				 * !msg.send(), isMessageLocal(msg), 1); deliverToHandler(msg,
				 * holder); } } else { // Publish
				 * metrics.messageReceived(msg.address(), !msg.send(),
				 * isMessageLocal(msg), handlers.list.size()); for
				 * (HandlerHolder holder: handlers.list) { deliverToHandler(msg,
				 * holder); } }
				 */
				return true;
			} else {
				// metrics.messageReceived(msg.address(), !msg.send(),
				// isMessageLocal(msg), 0);
				return false;
			}
		}
		return false;
	}

}
