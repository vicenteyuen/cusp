/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;

import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.core.modules.AbstractContainerModule;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.impl.EventBusImpl;

import com.google.inject.Binder;

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
		
		//EventBusImpl ebi = new EventBusImpl();
		this.bind(EventBus.class).to(EventBusImpl.class);
		

		/*
		Iterator<ServEngine> engineIter =  engines.iterator();
		while (engineIter.hasNext()){
			ServEngine engineItem =  engineIter.next();
			engineItem.setRunningContainer(this.getContainer());
			engineItem.start();
		}
		*/
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
