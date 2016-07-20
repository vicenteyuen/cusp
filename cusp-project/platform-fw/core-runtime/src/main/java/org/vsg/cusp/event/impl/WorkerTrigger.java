/**
 * 
 */
package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.Message;
import org.zeromq.ZMQ.Socket;

/**
 * @author Vicente Yuen
 *
 */
public class WorkerTrigger {
	
	private Socket replySocket;

	
	public Socket getReplySocket() {
		return replySocket;
	}

	public void setReplySocket(Socket replySocket) {
		this.replySocket = replySocket;
	}

	public void receiveMessage(Message msg) {
		
		System.out.println( msg.body());
		
	}
	
	public void trigger() {
		replySocket.send(new String("hello reply"));
	}
	

	

}
