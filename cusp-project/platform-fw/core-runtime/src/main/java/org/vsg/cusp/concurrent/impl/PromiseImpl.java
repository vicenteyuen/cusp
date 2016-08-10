package org.vsg.cusp.concurrent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.concurrent.Callback;
import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.ExecTaskFuture;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.event.AsyncResult;
import org.vsg.cusp.event.DeliveryOptions;
import org.vsg.cusp.event.Handler;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.MessageProducer;

public class PromiseImpl implements Promise {

	private static Logger logger = LoggerFactory.getLogger( PromiseImpl.class );
	

	
	private AbstractExecutorService execService = new ThreadPoolExecutor(10 , 5000 , 10l , TimeUnit.SECONDS , new LinkedBlockingDeque<Runnable>());;
	
	public AbstractExecutorService getExecService() {
		return execService;
	}

	public void setExecService(AbstractExecutorService execService) {
		this.execService = execService;
	}
	
	
	private List<OperationEvent> operationEvents = new ArrayList<OperationEvent>();



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
					/*
					EventBus eventBus = flow.getEventBus();
					DeliveryOptions options = new DeliveryOptions();
					options.setCodecName( "operation-event" );
					MessageProducer producer = eventBus.sender(EventFlow.EVB_CHANNEL , options);
					
					producer.send(operEvent, handler);

					// ---- count value ---
					countDownLatch.countDown();
					*/
					
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



	@Override
	public void onDone(Object result, Throwable error) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	

	
	
	
	



}
