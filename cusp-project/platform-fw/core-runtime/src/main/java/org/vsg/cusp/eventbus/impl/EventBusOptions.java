/**
 * 
 */
package org.vsg.cusp.eventbus.impl;

import java.util.concurrent.ExecutorService;

/**
 * @author Vicente Yuen
 *
 */
public class EventBusOptions {
	
	 /**
	  * The default port to use when clustering = 0 (meaning assign a random port)
	  */
	public static final int DEFAULT_CLUSTER_PORT = 5066;
	
	
	private int workerPort = 5066;
	
	public int getPort() {
		return workerPort;
	}
	
	
	private int brokerPort = 5051;
	
	public int getBrokerPort() {
		return this.brokerPort;
	}
	
	
	private ExecutorService executorService;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	

}
