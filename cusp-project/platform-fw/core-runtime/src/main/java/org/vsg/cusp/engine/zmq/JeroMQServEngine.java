/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Container;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.event.impl.DefaultMessageExchangeEncoder;

/**
 * @author Vicente Yuen
 *
 */
public class JeroMQServEngine implements ServEngine , Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(JeroMQServEngine.class);	
	
	private Map<String, String> arguments;	
	
	private Container container;		

	@Override
	public void setRunningContainer(Container container) {
		// TODO Auto-generated method stub
		this.container = container;
	}

	@Override
	public void init(Map<String, String> arguments) {
		// TODO Auto-generated method stub
		this.arguments = arguments;		
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		Thread threadHook = new Thread(this);
		threadHook.start();
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ReqRepBroker broker = new ReqRepBroker();
		// --- internal worker ---
		ReqRepWorker worker = new ReqRepWorker();
		// --- message exchange encoder ---
		DefaultMessageExchangeEncoder dme = new DefaultMessageExchangeEncoder();
		worker.setEncoder(dme);
		
		ExecutorService execService = Executors.newCachedThreadPool();
		
		execService.execute( broker );
		execService.execute( worker );
		execService.shutdown();
		
	}

	
	
}
