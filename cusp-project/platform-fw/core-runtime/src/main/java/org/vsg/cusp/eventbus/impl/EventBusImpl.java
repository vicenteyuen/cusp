package org.vsg.cusp.eventbus.impl;

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
import org.zeromq.ZMQ.Context;

public class EventBusImpl implements EventBus {
	
	private static Logger logger = LoggerFactory.getLogger( EventBusImpl.class );
	
	private Context zmqContext;
	
	public EventBusImpl() {
		//this.zmqContext = zmqContext;
		
		init();
	}
	
	private void init() {
		
	}

	@Override
	public EventBus send(String address, Object message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> EventBus send(String address, Object message,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus send(String address, Object message, DeliveryOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> EventBus send(String address, Object message,
			DeliveryOptions options,
			Handler<AsyncResult<Message<T>>> replyHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus publish(String address, Object message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus publish(String address, Object message,
			DeliveryOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageConsumer<T> consumer(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageConsumer<T> consumer(String address,
			Handler<Message<T>> handler) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageProducer<T> sender(String address, DeliveryOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageProducer<T> publisher(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MessageProducer<T> publisher(String address,
			DeliveryOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus registerCodec(MessageCodec codec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus unregisterCodec(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> EventBus registerDefaultCodec(Class<T> clazz,
			MessageCodec<T, ?> codec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventBus unregisterDefaultCodec(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Handler<AsyncResult<Void>> completionHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(Handler<AsyncResult<Void>> completionHandler) {
		// TODO Auto-generated method stub

	}

}
