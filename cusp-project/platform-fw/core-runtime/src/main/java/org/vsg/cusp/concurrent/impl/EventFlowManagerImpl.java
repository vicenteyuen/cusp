package org.vsg.cusp.concurrent.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.EventFlowManager;

public class EventFlowManagerImpl implements EventFlowManager {
	
	
	private Map<String, EventFlow> _efInstBinding = new LinkedHashMap<String , EventFlow>();
	

	@Override
	public EventFlow getFlow(String flowId) {
		// TODO Auto-generated method stub
		EventFlow  eventFlow =  _efInstBinding.get(flowId);
		if (null == eventFlow) {
			EventFlowImpl efi = new EventFlowImpl();
			eventFlow = efi;
			_efInstBinding.put( flowId , eventFlow);
		}
		
		
		return eventFlow;
	}

}
