package org.vsg.cusp.eventbus;

import org.vsg.cusp.concurrent.AsyncResult;
import org.vsg.cusp.concurrent.Handler;
import org.vsg.cusp.event.DeliveryOptions;
import org.vsg.cusp.event.Message;

public interface MessageProducer<T> {

	  int DEFAULT_WRITE_QUEUE_MAX_SIZE = 1000;

	  /**
	   * Synonym for {@link #write(Object)}.
	   *
	   * @param message  the message to send
	   * @return  reference to this for fluency
	   */
	  MessageProducer<T> send(T message);

	  <R> MessageProducer<T> send(T message, Handler<AsyncResult<Message<R>>> replyHandler);

	  MessageProducer<T> exceptionHandler(Handler<Throwable> handler);

	  MessageProducer<T> write(T data);

	  MessageProducer<T> setWriteQueueMaxSize(int maxSize);

	  MessageProducer<T> drainHandler(Handler<Void> handler);

	  /**
	   * Update the delivery options of this producer.
	   *
	   * @param options the new options
	   * @return this producer object
	   */
	  MessageProducer<T> deliveryOptions(DeliveryOptions options);

	  /**
	   * @return The address to which the producer produces messages.
	   */
	  String address();

	  /**
	   * Closes the producer, calls {@link #close()}
	   */
	  void end();

	  /**
	   * Closes the producer, this method should be called when the message producer is not used anymore.
	   */
	  void close();	
	
}
