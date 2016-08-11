package org.vsg.cusp.event;

/**
 * arrange message queue 
 * @author vison
 *
 */
public interface MessageQueueBox {
	
	
	void receiveMessage(Message<byte[]> message);
	
	

}
