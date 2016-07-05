package org.vsg.cusp.concurrent;


public class MockOperationEventCls {


	
	@EventInfo(id="testCase1")
	public <U> void execTestCase1( EventFlowManager flowManager) throws Exception {
		
		EventFlow  eventFlow =  flowManager.getFlow("test2");
		
		Promise promInst = eventFlow.promise(EventFlow.MODE_LOCAL);
	
		OperationEvent currentEvent = eventFlow.getOperEvent("testCase1@" + MockOperationEventCls2.class.getName());
		promInst.addOperationEvent( currentEvent );

		System.out.println("flow manager 1 start : " + eventFlow);			
		
		promInst.sync();

		System.out.println("flow manager 1 end : " + eventFlow);			
	}
	
	
	@EventInfo(id="testCase2")
	public <U> void execTestCase2( EventFlowManager flowManager) throws Exception {

		System.out.println("flow manager 2: " + flowManager);
		
	}
	
	@EventInfo(id="testCase3")
	public <U> void execTestCase3(EventFlowManager flowManager) throws Exception {

		System.out.println("flow manager 3: " + flowManager);
		
	}	

	@EventInfo(id="testCase4")
	public <U> void execTestCase4(EventFlowManager flowManager) throws Exception {

		System.out.println("flow manager 4: " + flowManager);
		
	}
	
	@EventInfo(id="testCase5")
	public <U> void execTestCase5(EventFlowManager flowManager) throws Exception {
		System.out.println("flow manager 5: " + flowManager);
		
	}		
	
}
