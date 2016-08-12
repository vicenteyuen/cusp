package org.vsg.cusp.event;

/**
 * arrange message queue 
 * @author vison
 *
 */
public interface MessageQueueBox<T> {
	
	
	void handle(Message<T> message);
	
	

}
