package org.vsg.cusp.apps.event;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.EventInfo;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.event.flow.Promise;


public class TestOperationEventCls {


	
	@EventInfo(id="testCase1")
	public <U> void execTestCase1( FlowManager flowManager) throws Exception {
		
		EventFlow  eventFlow =  flowManager.getFlow("test2");
		
		Promise promInst = eventFlow.promise(EventFlow.MODE_LOCAL);
	
		OperationEvent currentEvent = eventFlow.getOperEvent("testCase1@" + TestOperationEventCls.class.getName());
		promInst.addOperationEvent( currentEvent );

		System.out.println("flow manager 1 start : " + eventFlow);			
		
		promInst.sync();

		System.out.println("flow manager 1 end : " + eventFlow);			
	}
	
	
	@EventInfo(id="testCase2")
	public <U> void execTestCase2( FlowManager flowManager) throws Exception {

		System.out.println("flow manager 2: " + flowManager);
		
	}
	
	@EventInfo(id="testCase3")
	public <U> void execTestCase3(FlowManager flowManager) throws Exception {

		System.out.println("flow manager 3: " + flowManager);
		
	}	

	@EventInfo(id="testCase4")
	public <U> void execTestCase4(FlowManager flowManager) throws Exception {

		System.out.println("flow manager 4: " + flowManager);
		
	}
	
	@EventInfo(id="testCase5")
	public <U> void execTestCase5(FlowManager flowManager) throws Exception {
		System.out.println("flow manager 5: " + flowManager);
		
	}		
	
}
