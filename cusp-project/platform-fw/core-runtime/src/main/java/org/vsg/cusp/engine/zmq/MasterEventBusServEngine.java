/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Container;
import org.vsg.cusp.core.CountDownLatchAware;
import org.vsg.cusp.core.EngineCompLoaderService;
import org.vsg.cusp.core.LifecycleState;
import org.vsg.cusp.core.MicroCompInjector;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.event.annotations.BeanService;

import com.google.inject.Injector;

/**
 * @author Vicente Yuen
 *
 */
public class MasterEventBusServEngine implements ServEngine , Runnable,CountDownLatchAware , EngineCompLoaderService {
	
	private static Logger logger = LoggerFactory.getLogger(MasterEventBusServEngine.class);	
	
	private Map<String, String> arguments;	
	
	private Container container;
	
	private Runnable reqRepBroker;
	
	private Runnable worker;
	
	@Inject
	public MasterEventBusServEngine(@Named("RequestResponseBroker") Runnable reqRepBroker,
			@Named("RequestResponseWorker") Runnable worker) {
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
		// --- start lifecycle state ---
		setState( LifecycleState.STARTING );
		
		ExecutorService execService = Executors.newCachedThreadPool();

		execService.execute( reqRepBroker );
		execService.execute( worker );
		execService.shutdown();

		
		
		// --- count down the current handle ---
		countDownLatch.countDown();
		
		setState( LifecycleState.STARTED );
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


	@Override
	public void doCompInject(MicroCompInjector microCompInjector) {
		Injector injector = microCompInjector.getInjector();
		
		
		System.out.println("injector : " + injector);
		/*
		Collection<Class<?>> supportedCls = supportAnnotationClz(microCompInjector.getAnnotationMaps());
		
		for (Class<?> cls : supportedCls) {
			Object inst =  injector.getInstance( cls );
			
			// --- regist bean ----
			System.out.println("inst " + inst);
			
		}
		*/		
		
	}
	
	private Collection<Class<?>> supportAnnotationClz(Map<Class<?>, Collection<Class<?>>>  annotationMap ) {
		
		Collection<Class<?>> result = new Vector<Class<?>>();
		
		Set<Entry<Class<?>, Collection<Class<?>>>> entryMapSet =   annotationMap.entrySet();
		
		for (Entry<Class<?>, Collection<Class<?>>> entry : entryMapSet) {
			 Collection<Class<?>> annotations =  entry.getValue();
			 
			 if (!annotations.contains( BeanService.class )) {
				 continue;
			 }
			 
			 result.add( entry.getKey() );
			
		}
		
		
		return result;
	}	

	
	
}
