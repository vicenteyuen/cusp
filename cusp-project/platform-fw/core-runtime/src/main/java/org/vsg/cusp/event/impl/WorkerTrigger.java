/**
 * 
 */
package org.vsg.cusp.event.impl;

import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.eventbus.impl.CodecManager;
import org.vsg.cusp.eventbus.spi.Buffer;
import org.zeromq.ZMQ.Socket;

import com.google.common.primitives.Ints;

/**
 * @author Vicente Yuen
 *
 */
public class WorkerTrigger {
	
	private Socket replySocket;
	
	private CodecManager codecManager;	

	public CodecManager getCodecManager() {
		return codecManager;
	}

	public void setCodecManager(CodecManager codecManager) {
		this.codecManager = codecManager;
	}

	public Socket getReplySocket() {
		return replySocket;
	}

	public void setReplySocket(Socket replySocket) {
		this.replySocket = replySocket;
	}
	
	

	public void receiveMessage(Message<byte[]> msg) {
		
		// --- parse content ---
		byte[] msgBody = msg.body();
		
		int locFrom = 0;
		int locTo = locFrom + Ints.BYTES;
		int msgBodyLength = Ints.fromByteArray( java.util.Arrays.copyOfRange(msgBody, locFrom, locTo) );

		locFrom = locTo;
		locTo = locFrom + msgBodyLength;		
		
		String  msgCodecName = new String(java.util.Arrays.copyOfRange(msgBody, locFrom, locTo) );
		
		if ("null".equals(msgCodecName)) {
			
		}
		else if ("operation-event".equals(OperationEventMessageCodec.NAME))  {
			
			MessageCodec<?,OperationEvent>  msgCodec =  codecManager.getCodec(msgCodecName);
			
			Buffer buffer = Buffer.factory.buffer( java.util.Arrays.copyOfRange(msgBody, locTo, msgBody.length) );
			
			OperationEvent event = msgCodec.decodeFromWire( 0 , buffer);
			

			//codecManager.decodeFromWire();
			
		}
		




		
	}
	
	public void trigger() {
		replySocket.send(new String("hello reply"));
	}
	

	

}
