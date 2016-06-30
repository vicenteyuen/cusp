/**
 * 
 */
package org.vsg.cusp.eventbus.impl;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * @author vison
 *
 */
public class ZmqEndPointManager {
	
	
	private  MsgExchangeProtocol excangeProtocol;

	/**
	 * 
	 */
	public ZmqEndPointManager() {
		// TODO Auto-generated constructor stub
	}

	public MsgExchangeProtocol getExcangeProtocol() {
		return excangeProtocol;
	}

	public void setExcangeProtocol(MsgExchangeProtocol excangeProtocol) {
		this.excangeProtocol = excangeProtocol;
	}
	
	public void messageSent(MessageImpl message ,EventBusOptions options) {
		Context clientContext = ZMQ.context(zmq.ZMQ.ZMQ_IO_THREADS);
		// Socket to talk to server
		Socket requester = clientContext.socket(ZMQ.REQ);

		StringBuilder connProtocol = new StringBuilder("tcp://");
		connProtocol.append("localhost");
		connProtocol.append(":").append(options.getBrokerPort());
		

		requester.connect(connProtocol.toString());
		
		if (null != excangeProtocol) {
			byte[] content = excangeProtocol.encode(message);
			requester.send(content, 0);
		}
		requester.close();
		clientContext.term();
	}

}
