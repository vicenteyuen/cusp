package org.vsg.cusp.event.common;

public interface Service {

	/**
	  * Starts the service. This method blocks until the service has completely started.
	 */	
	void start() throws Exception;

	/**
	  * Stops the service. This method blocks until the service has completely shut down.
	  */	
	void stop();
}
