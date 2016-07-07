/**
 * 
 */
package org.vsg.cusp.event.impl;

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
		Context clientContext = ZMQ.context(zmq.ZMQ.ZMQ_IO_THREADS);
		// Socket to talk to server
		Socket requester = clientContext.socket(ZMQ.REQ);

		StringBuilder connProtocol = new StringBuilder("tcp://");
		connProtocol.append("localhost");
		connProtocol.append(":").append(options.getBrokerPort());
		requester.connect(connProtocol.toString());

		
		if (null != encoder) {
			byte[] content = encoder.encode(message);
			requester.send(content, 0);
		}
		requester.close();
		clientContext.term();
	}

}
