/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.lang.reflect.Method;

import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.eventbus.MultiMap;
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
			
			int maxLength = msgBody.length - locTo;
			Buffer buffer = Buffer.buffer( maxLength );
			buffer.appendBytes( java.util.Arrays.copyOfRange(msgBody, locTo, msgBody.length)  );
			OperationEvent event = msgCodec.decodeFromWire( 0 , buffer);
			
			scheduleAndExecuteEvent(event);
			// --- arrange and execute object ---
			
			Message<byte[]> respMsg = createResponseMsg(msg);
			
			System.out.println(respMsg.headers());
			

			//codecManager.decodeFromWire();
			
		}
		
	}
	
	private void scheduleAndExecuteEvent(OperationEvent event) {
		String clsName = event.assoClassName();
		
		try {
			Class<?> objCls = Thread.currentThread().getContextClassLoader().loadClass( clsName );
			
			Object _inst = objCls.newInstance();
			
			Method method = event.assoBindMethod();

			//method.invoke( _inst , event.getRuntimeArgument());
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// --- arrange response message---
	}
	
	private Message<byte[]> createResponseMsg(Message<byte[]> reqMsg) {
		ByteArrayMessageImpl msgInst = new ByteArrayMessageImpl();
		msgInst.setMsgType( Message.TYPE_REP );
		
		// --- construct header ---
		MultiMap headers = MultiMap.caseInsensitiveMultiMap();
		headers.add( Message.HeaderKey.REPLIER , reqMsg.headers().get( Message.HeaderKey.PUBLISHER ));
		headers.add( Message.HeaderKey.SENT_TIME , Long.toString( System.currentTimeMillis() ));
		
		String fullCorrId = reqMsg.headers().get( Message.HeaderKey.CORRID );
		String[] corrIds = fullCorrId.split("\\.");
		
		int corrSeq = Integer.parseInt( corrIds[2] );
		int newSeq = corrSeq+1;
		
		StringBuilder corrId = new StringBuilder(corrIds[0]);
		corrId.append(".").append( corrIds[1] );
		corrId.append(".").append( Integer.toString( newSeq ) );
		headers.add( Message.HeaderKey.CORRID , corrId.toString());

		msgInst.setHeaders( headers );
		
		return msgInst;
	}
	
	
	public void trigger() {
		replySocket.send(new String("hello reply"));
	}
	

	

}
