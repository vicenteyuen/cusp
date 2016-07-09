package org.vsg.cusp.event.impl;

import javax.inject.Provider;

import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.impl.EventBusOptions;

public class EventBusProvider implements Provider<EventBus> {
	
	private EventBusOptions options;
	
	public EventBusOptions getOptions() {
		return options;
	}

	public void setOptions(EventBusOptions options) {
		this.options = options;
	}
	
	private ZmqEventBusImplEndPoint eventBusInst;
	

	@Override
	public EventBus get() {
		// TODO Auto-generated method stub
		if (null == eventBusInst) {
			
			ZmqEventBusImplEndPoint eventBusImpl = new ZmqEventBusImplEndPoint(options);
			
			/**
			 * handle define event
			 */
			Handler<AsyncResult<Void>> completionHandler = new Handler<AsyncResult<Void>>() {

				@Override
				public void handle(AsyncResult<Void> event) {
					// TODO Auto-generated method stub
					System.out.println(event);
				}
				
			};
			eventBusImpl.start(completionHandler);	
			
		}
		
		return eventBusInst;
	}

}
