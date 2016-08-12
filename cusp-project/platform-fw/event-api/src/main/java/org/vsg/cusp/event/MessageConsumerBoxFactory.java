package org.vsg.cusp.event;

import org.vsg.cusp.eventbus.MessageConsumer;

/**
 * Return queue handle factory
 * @author Vicente Yuen
 *
 */
public interface MessageConsumerBoxFactory {

	/**
	 * get queue address
	 * @param address
	 * @return
	 */
	<T> MessageConsumer<T> getConsumer(String address);
	
	
}
