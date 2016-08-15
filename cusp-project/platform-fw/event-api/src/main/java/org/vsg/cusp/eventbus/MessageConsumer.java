package org.vsg.cusp.eventbus;

import org.vsg.cusp.concurrent.AsyncResult;
import org.vsg.cusp.core.Handler;
import org.vsg.cusp.event.Message;

public interface MessageConsumer<T> {

	MessageConsumer<T> exceptionHandler(Handler<Throwable> handler);

	MessageConsumer<T> handler(Handler<Message<T>> handler);

	MessageConsumer<T> pause();

	MessageConsumer<T> resume();

	MessageConsumer<T> endHandler(Handler<Void> endHandler);

	/**
	 * @return true if the current consumer is registered
	 */
	boolean isRegistered();

	/**
	 * @return The address the handler was registered with.
	 */
	String address();

	/**
	 * Set the number of messages this registration will buffer when this stream
	 * is paused. The default value is <code>0</code>. When a new value is set,
	 * buffered messages may be discarded to reach the new value.
	 *
	 * @param maxBufferedMessages
	 *            the maximum number of messages that can be buffered
	 * @return this registration
	 */
	MessageConsumer<T> setMaxBufferedMessages(int maxBufferedMessages);

	/**
	 * @return the maximum number of messages that can be buffered when this
	 *         stream is paused
	 */
	int getMaxBufferedMessages();

	/**
	 * Optional method which can be called to indicate when the registration has
	 * been propagated across the cluster.
	 *
	 * @param completionHandler
	 *            the completion handler
	 */
	void completionHandler(Handler<AsyncResult<Void>> completionHandler);

	/**
	 * Unregisters the handler which created this registration
	 */
	void unregister();

	/**
	 * Unregisters the handler which created this registration
	 *
	 * @param completionHandler
	 *            the handler called when the unregister is done. For example in
	 *            a cluster when all nodes of the event bus have been
	 *            unregistered.
	 */
	void unregister(Handler<AsyncResult<Void>> completionHandler);

}
