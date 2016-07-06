package org.vsg.cusp.concurrent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.ExecTaskFuture;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.Message;
import org.vsg.cusp.eventbus.MessageProducer;

public class PromiseImpl implements Promise {

	private static Logger logger = LoggerFactory.getLogger( PromiseImpl.class );
	
	private EventFlow flow;
	
	public EventFlow getFlow() {
		return flow;
	}

	public void setFlow(EventFlow flow) {
		this.flow = flow;
	}
	
	private AbstractExecutorService execService = new ThreadPoolExecutor(10 , 5000 , 10l , TimeUnit.SECONDS , new LinkedBlockingDeque<Runnable>());;
	
	public AbstractExecutorService getExecService() {
		return execService;
	}

	public void setExecService(AbstractExecutorService execService) {
		this.execService = execService;
	}
	
	
	private List<OperationEvent> operationEvents = new ArrayList<OperationEvent>();


	@Override
	public Promise addOperationEvent(OperationEvent event) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(event);

		operationEvents.add( event );

		return this;
	}
	

	@Override
	public Promise await() {
		
		// --- execute event ---
		return this;
	}

	@Override
	public Promise sync() {
		
		
		notifyEventssNow();
		
		return this;
	}
	
	
	private void notifyEventssNow() {
		
		
		/**
		 * Send pre-define operation event and get the reponse message first.  
		 */
		
		CountDownLatch countDownLatch = new CountDownLatch(operationEvents.size());
		
		
		
		Collection<ExecTaskFuture> taskFutureInSameGroup = new Vector<ExecTaskFuture>();
		
		Handler<AsyncResult<Message>> handler = new Handler<AsyncResult<Message>>() {

			@Override
			public void handle(AsyncResult<Message> future) {
				// ---- receve message ----
				
				
				System.out.println("hello message ");
				
			}
			
		};
		
		
		ListIterator<OperationEvent>  listIter =  operationEvents.listIterator();
		while ( listIter.hasNext() ) {
			OperationEvent operEvent = listIter.next();
			
			// --- 
			Runnable command = new Runnable() {

				@Override
				public void run() {
					EventBus eventBus = flow.getEventBus();
					
					MessageProducer producer = eventBus.sender(EventFlow.EVB_CHANNEL);
					producer.send(operEvent, handler);

	
					//eventBus.p
					// --- send event msg to run ---
					// execute method 
					/*
					Method bindMethod = operEvent.assoBindMethod();
					
					Class<?>  cls = Class.forName( operEvent.assoClassName() );
					Object inst = cls.newInstance();
					bindMethod.invoke( inst ,flow.getFlowManager());
					*/
					
					// ---- count value ---
					countDownLatch.countDown();
					
				}
				
			};
			execService.execute(command);
			 
		}
		
		execService.shutdown();
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		/**
		 * waiting for job task complete and fire 
		 */
	
	
	}
	
	
	

	
	
	
	



}
