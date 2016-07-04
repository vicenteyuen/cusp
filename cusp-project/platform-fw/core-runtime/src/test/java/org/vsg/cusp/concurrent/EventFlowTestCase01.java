package org.vsg.cusp.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.vsg.cusp.concurrent.impl.EventFlowManagerImpl;
import org.vsg.cusp.concurrent.impl.PromiseImpl;

public class EventFlowTestCase01 {
	
	
	private EventFlow eventFlow;
	
	public void execute() {
	
		EventFlowManagerImpl efManager = new EventFlowManagerImpl();
		eventFlow = efManager.getFlow("testcase");
		
		
		
		
		Promise prom = eventFlow.promise( EventFlow.MODE_LOCAL );
	
		PromiseImpl piInst = (PromiseImpl)prom;
		piInst.setExecService( Executors.newCachedThreadPool() );

		

		GenericFutureListener gfl = new GenericFutureListener() {

			@Override
			public void operationComplete(Future future) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("running test 1");
			}


		};
		prom.addListener( gfl );
		
		
		GenericFutureListener gf2 = new GenericFutureListener() {

			@Override
			public void operationComplete(Future future) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("running test 2");
			}

		};
		
		
		prom.addListener( gf2 );
		

		GenericFutureListener gf3 = new GenericFutureListener() {

			@Override
			public void operationComplete(Future future) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("running test 3");
			}

		};
		
		
		prom.addListener( gf3 );
		
		
		GenericFutureListener gf4 = new GenericFutureListener() {

			@Override
			public void operationComplete(Future future) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("running test 4");
			}

		};
		
		
		prom.addListener( gf4 );
		
		
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
