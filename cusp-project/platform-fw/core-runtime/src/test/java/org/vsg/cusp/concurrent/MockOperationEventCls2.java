package org.vsg.cusp.concurrent;

import org.vsg.cusp.event.annotations.EventInfo;
import org.vsg.cusp.event.flow.FlowManager;


public class MockOperationEventCls2 {


	
	@EventInfo(id="testCase1")
	public <U> void execTestCase1( FlowManager flowManager) throws Exception {

		
		
		System.out.println(" MockOperationEventCls2 running ok" );
		
	}

	
}
