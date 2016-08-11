package org.vsg.cusp.event.engine.modules;

import org.vsg.cusp.concurrent.Handler;
import org.vsg.cusp.core.Service;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.eventbus.MessageConsumer;

public class EventWorkerService implements Service {

	private MessageConsumer<byte[]> consumer;
	
	@Override
	public void start() throws Exception {
		
		// ---- 
		Handler<Message<byte[]>> handler = new Handler<Message<byte[]>>() {

			@Override
			public void handle(Message<byte[]> event) {
				// TODO Auto-generated method stub
				
			}
			
		};
		consumer.handler(handler);
		
		Handler<Void> voidhandler = new Handler<Void>() {

			@Override
			public void handle(Void event) {
				// TODO Auto-generated method stub
				
			}


			
		};		
		consumer.endHandler(voidhandler);
		


	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
