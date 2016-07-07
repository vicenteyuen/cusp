package org.vsg.cusp.eventbus;

import org.vsg.cusp.event.Message;

public interface SendContext<T> {

	  /**
	   * @return  The message being sent
	   */
	  Message<T> message();

	  /**
	   * Call the next interceptor
	   */
	  void next();

	  /**
	   *
	   * @return true if the message is being sent (point to point) or False if the message is being published
	   */
	  boolean send();	
	
}
