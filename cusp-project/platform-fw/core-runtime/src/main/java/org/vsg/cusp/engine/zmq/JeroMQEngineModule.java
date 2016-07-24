/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.core.modules.AbstractContainerModule;
import org.vsg.cusp.event.common.Service;
import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.event.impl.MultiThreadEngineService;
import org.vsg.cusp.eventbus.EventBus;

import com.google.inject.Scopes;

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
		binder().bind(ServEngine.class).to( JeroMQServEngine.class ).in( Scopes.SINGLETON );
		
		
		
		//JeroMQServEngine servEngine = new JeroMQServEngine();
		
		//servEngine.start();
		
		
	}
	
	
	
	

	@Override
	public void run() {
		

		
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
