/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Container;
import org.vsg.cusp.core.CountDownLatchAware;
import org.vsg.cusp.core.EventBusServEngine;
import org.vsg.cusp.core.LifecycleState;

/**
 * @author Vicente Yuen
 *
 */
public class JeroMQServEngine implements EventBusServEngine , Runnable ,  CountDownLatchAware {
	
	private static Logger logger = LoggerFactory.getLogger(JeroMQServEngine.class);	
	
	private Map<String, String> arguments;	
	
	private Container container;
	
	
	
	private RunnableFuture reqRepBroker;
	
	private RunnableFuture worker;
	
	@Inject
	public JeroMQServEngine(@Named("RequestResponseBroker") RunnableFuture reqRepBroker,
			@Named("RequestResponseWorker") RunnableFuture worker) {
		this.reqRepBroker = reqRepBroker;
		this.worker = worker;
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
		

		// --- message exchange encoder ---
		//DefaultMessageExchangeEncoder dme = new DefaultMessageExchangeEncoder();
		//worker.setEncoder(dme);
		
		ExecutorService execService = Executors.newCachedThreadPool();

		execService.execute( reqRepBroker );
		execService.execute( worker );
		execService.shutdown();
		
		
		countDownLatch.countDown();
		
	}


    private volatile LifecycleState state = LifecycleState.NEW;
    
	@Override
	public LifecycleState getState() {
		// TODO Auto-generated method stub
		return state;
	}
	

	@Override
	public void setState(LifecycleState newState) {
		// TODO Auto-generated method stub
		state = newState;
	}	

	
	private CountDownLatch countDownLatch;
	
	@Override
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;		
	}

	
	
}
