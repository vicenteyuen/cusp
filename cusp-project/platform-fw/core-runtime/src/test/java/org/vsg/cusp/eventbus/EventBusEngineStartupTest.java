package org.vsg.cusp.eventbus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.eventbus.impl.EventBusEngine;
import org.vsg.cusp.eventbus.impl.EventBusOptions;

public class EventBusEngineStartupTest {
	
	private static Logger log = LoggerFactory.getLogger( EventBusEngineStartupTest.class );
	
	private EventBusEngine engine;
	
	public void startup() throws Exception{

		EventBusOptions options = new EventBusOptions();
		ExecutorService  executorService =  Executors.newFixedThreadPool(5);
		options.setExecutorService( executorService );
		
		engine = new EventBusEngine(options);
		engine.startup();

		
	}
	
	public void shutdown() {
		
	}
	

	public static void main(String[] args) throws Exception {
		
		// --- start engine ----
		EventBusEngineStartupTest ebeStartup = new EventBusEngineStartupTest();
		ebeStartup.startup();
		
		log.info("Startup EventBus TestEngine");


	}

}
