/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.core.modules.AbstractContainerModule;
import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.eventbus.EventBus;

/**
 * @author Vicente Yuen
 *
 */
public class JeroMQEngineModule extends AbstractContainerModule implements ServEngine, Runnable{



	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		
		// --- start mqbroker ---
		
		
		//EventBusImpl ebi = new EventBusImpl();
		this.bind(EventBus.class).to(ZmqEventBusImplEndPoint.class);
		

		/*
		Iterator<ServEngine> engineIter =  engines.iterator();
		while (engineIter.hasNext()){
			ServEngine engineItem =  engineIter.next();
			engineItem.setRunningContainer(this.getContainer());
			engineItem.start();
		}
		*/
		
		// --- start mq broker --
		
		Thread mqstartThread = new Thread(this);
		mqstartThread.start();
		
		
	}
	
	
	
	

	@Override
	public void run() {
		
		// --- create Request Response MQ Quere ---
		ReqRepBroker rrbroker = new ReqRepBroker();
		ReqRepWorker rrWorker = new ReqRepWorker();

		
		
		
		ScheduledThreadPoolExecutor  stpe = new ScheduledThreadPoolExecutor(10);
		
		
		
		stpe.execute( rrWorker);
		stpe.execute( rrbroker );
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private Map<String, String> arguments;	

	@Override
	public void init(Map<String, String> arguments) {
		// TODO Auto-generated method stub
		this.arguments = arguments;		
	}
	
	
	
}
