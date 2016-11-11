package org.vsg.cusp.platform.core.api;

import org.vsg.common.async.AsyncResult;
import org.vsg.common.async.Callback;

public interface Service {

	/**
	  * Starts the service. This method blocks until the service has completely started.
	 */	
	<T extends Object> void start(Callback<AsyncResult<T>> callback) throws StartException;

	/**
	  * Stops the service. This method blocks until the service has completely shut down.
	  */	
	<T extends Object> void stop(Callback<AsyncResult<T>> callback);
}
