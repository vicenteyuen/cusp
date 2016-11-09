package org.vsg.cusp.eventbus;

public interface EventBusAware {
	
	/**
	 * inject the handle event bus implement
	 * @param bus
	 */
	void setEventBus(EventBus bus);

}
