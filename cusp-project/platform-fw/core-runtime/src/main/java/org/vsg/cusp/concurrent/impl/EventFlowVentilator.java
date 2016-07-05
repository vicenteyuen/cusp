package org.vsg.cusp.concurrent.impl;

import java.util.concurrent.Callable;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class EventFlowVentilator implements Callable<Socket> {

	@Override
	public Socket call() throws Exception {
		//  Socket to send messages on
		ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5557");
        
        
		return sender;
	}



}
