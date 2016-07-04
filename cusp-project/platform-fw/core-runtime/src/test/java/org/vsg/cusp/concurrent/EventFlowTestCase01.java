package org.vsg.cusp.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.vsg.cusp.concurrent.impl.EventFlowManagerImpl;
import org.vsg.cusp.concurrent.impl.PromiseImpl;
import org.vsg.cusp.eventbus.AsyncResult;

public class EventFlowTestCase01 {
	
	
	private EventFlow eventFlow;
	
	public void execute() {
	
		EventFlowManagerImpl efManager = new EventFlowManagerImpl();
		eventFlow = efManager.getFlow("testcase");
		
		
		
		
		Promise prom = eventFlow.promise( EventFlow.MODE_LOCAL );
	
		PromiseImpl piInst = (PromiseImpl)prom;
		
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10 , 5000 , 10l , TimeUnit.SECONDS , new LinkedBlockingDeque<Runnable>());
		piInst.setExecService( poolExecutor );

		


		
		try {
			prom.sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//CompletionStage  cs = eventFlow.getRunStage(EventFlow.MODE_LOCAL);
		
		/*
		cs.thenRunAsync(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("hello message");
			}
			
		});
		
		System.out.println(cs);
		*/
		

	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventFlowTestCase01 executeCase = new EventFlowTestCase01();
		executeCase.execute();

	}

}
