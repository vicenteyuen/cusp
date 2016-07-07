/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.util.List;

import org.vsg.cusp.eventbus.impl.EventBusOptions;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;
import org.vsg.cusp.eventbus.impl.MessageImpl;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * @author Vicente Yuen
 *
 */
public class ZmqcmdHelper {
	
	
	private  MessageExchangeEncoder encoder;

	private EventBusOptions options;

	/**
	 * 
	 */
	public ZmqcmdHelper(EventBusOptions options) {
		this.options = options;
		this.options.setCmdHelper(this);
	}

	public MessageExchangeEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(MessageExchangeEncoder encoder) {
		this.encoder = encoder;
	}
	
	public void messageSent(MessageImpl message ,EventBusOptions options) {

		
		List<String>  senderHosts =  options.getSenderHosts();
		// Socket to talk to server

		for (String senderHost : senderHosts) {
			Context clientContext = ZMQ.context(zmq.ZMQ.ZMQ_IO_THREADS);			
			
			Socket requester = clientContext.socket(ZMQ.REQ);
			requester.connect(senderHost);			
			if (null != encoder && senderHosts.size() > 0) {
				byte[] content = encoder.encode(message);
				requester.send(content, 0);
			}
			requester.close();
			clientContext.term();
		}
		


	}

}
