package org.vsg.cusp.event;

import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.DeliveryOptions;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.MultiMap;

public interface Message<T> {
	
	
	public static final byte TYPE_REQ = 1;
	
	public static final byte TYPE_REP = 2;
	
	
	/**
	 * Set the message type	
	 * @return
	 */
	byte msgType();
	

	/**
	 * The address the message was sent to
	 */
	String address();

	/**
	 * Multi-map of message headers. Can be empty
	 *
	 * @return the headers
	 */
	MultiMap headers();

	/**
	 * The body of the message. Can be null.
	 *
	 * @return the body, or null.
	 */
	T body();

	/**
	 * The reply address. Can be null.
	 *
	 * @return the reply address, or null, if message was sent without a reply
	 *         handler.
	 */
	String replyAddress();

	/**
	 * Reply to this message.
	 * <p>
	 * If the message was sent specifying a reply handler, that handler will be
	 * called when it has received a reply. If the message wasn't sent
	 * specifying a receipt handler this method does nothing.
	 *
	 * @param message
	 *            the message to reply with.
	 */
	void reply(Object message);

	/**
	 * The same as {@code reply(R message)} but you can specify handler for the
	 * reply - i.e. to receive the reply to the reply.
	 *
	 * @param message
	 *            the message to reply with.
	 * @param replyHandler
	 *            the reply handler for the reply.
	 */
	<R> void reply(Object message, Handler<AsyncResult<Message<R>>> replyHandler);

	/**
	 * Link {@link #reply(Object)} but allows you to specify delivery options
	 * for the reply.
	 *
	 * @param message
	 *            the reply message
	 * @param options
	 *            the delivery options
	 */
	void reply(Object message, DeliveryOptions options);

	/**
	 * The same as {@code reply(R message, DeliveryOptions)} but you can specify
	 * handler for the reply - i.e. to receive the reply to the reply.
	 *
	 * @param message
	 *            the reply message
	 * @param options
	 *            the delivery options
	 * @param replyHandler
	 *            the reply handler for the reply.
	 */
	<R> void reply(Object message, DeliveryOptions options,
			Handler<AsyncResult<Message<R>>> replyHandler);

	/**
	 * Signal to the sender that processing of this message failed.
	 * <p>
	 * If the message was sent specifying a result handler the handler will be
	 * called with a failure corresponding to the failure code and message
	 * specified here.
	 *
	 * @param failureCode
	 *            A failure code to pass back to the sender
	 * @param message
	 *            A message to pass back to the sender
	 */
	void fail(int failureCode, String message);

}
