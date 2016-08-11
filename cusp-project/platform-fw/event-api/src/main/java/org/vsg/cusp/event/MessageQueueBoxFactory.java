package org.vsg.cusp.event;

/**
 * Return queue handle factory
 * @author Vicente Yuen
 *
 */
public interface MessageQueueBoxFactory {

	/**
	 * get queue address
	 * @param address
	 * @return
	 */
	MessageQueueBox getBox(String address);
	
	
}
