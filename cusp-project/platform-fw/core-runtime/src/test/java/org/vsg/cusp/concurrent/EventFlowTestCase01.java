package org.vsg.cusp.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.vsg.cusp.concurrent.impl.FlowManagerOptions;
import org.vsg.cusp.concurrent.impl.PromiseImpl;
import org.vsg.cusp.engine.zmq.JeroMQServEngine;
import org.vsg.cusp.event.flow.impl.FlowManagerImpl;

public class EventFlowTestCase01 {
	
	
	private EventFlow eventFlow;
	
	
	
	public void start() {
		
		// --- start engine ---
		JeroMQServEngine servEngine = new JeroMQServEngine();
		
		servEngine.start();
		
		
	}
	
	
	
	public void execute() {
		
		FlowManagerOptions flowManagerOpts = new FlowManagerOptions();
		
	
		FlowManagerImpl efManager = new FlowManagerImpl(flowManagerOpts);
		efManager.init();
		eventFlow = efManager.getFlow("testcase");
		
		Promise prom = eventFlow.promise( EventFlow.MODE_LOCAL );
	
		PromiseImpl piInst = (PromiseImpl)prom;

		
		
		// --- add openeration event ---
		OperationEvent  event1 =  eventFlow.getOperEvent("testCase1@" + MockOperationEventCls.class.getName());
		OperationEvent  event2 =  eventFlow.getOperEvent("testCase2@" + MockOperationEventCls.class.getName());
		OperationEvent  event3 =  eventFlow.getOperEvent("testCase3@" + MockOperationEventCls.class.getName());
		OperationEvent  event4 =  eventFlow.getOperEvent("testCase4@" + MockOperationEventCls.class.getName());
		OperationEvent  event5 =  eventFlow.getOperEvent("testCase5@" + MockOperationEventCls.class.getName());
		
		try {
			
			prom.addOperationEvent(event1);
			prom.addOperationEvent(event2);
			prom.addOperationEvent(event3);
			prom.addOperationEvent(event4);
			prom.addOperationEvent(event5);
			
			// --- add promise on done event ---
			prom.sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	private List<Package> scanPackageByRef(String containPackageName) {
		 Package[] packages = Package.getPackages();
		 
		 List<Package> allList = new LinkedList<Package>();
		 for (Package  pkg : packages) {
			 if (pkg.getName().startsWith(containPackageName)) {
				 allList.add( pkg );
			 }
		 }
		 
		 return allList;
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventFlowTestCase01 executeCase = new EventFlowTestCase01();
		
		executeCase.start();
		
		
		executeCase.execute();

	}

}
