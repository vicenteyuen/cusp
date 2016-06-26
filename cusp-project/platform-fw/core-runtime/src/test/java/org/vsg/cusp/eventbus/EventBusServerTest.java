package org.vsg.cusp.eventbus;

import org.vsg.cusp.eventbus.impl.EventBusImpl;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class EventBusServerTest {

	
	private EventBusImpl ebi;
	
	public EventBusServerTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		Context context = ZMQ.context(1);
		
		ebi = new EventBusImpl();
		ebi.setZmqContext( context );
		
		
		Socket messageSocket = context.socket(ZMQ.ROUTER);

		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventBusServerTest ebt = new EventBusServerTest();
		
		ebt.start();

	}

}
