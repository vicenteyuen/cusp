/**
 * 
 */
package org.vsg.cusp.event.impl;

import javax.inject.Inject;

import org.vsg.cusp.core.Buffer;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.MessageInbox;
import org.vsg.cusp.event.OperationEvent;

import com.google.common.primitives.Ints;

/**
 * @author ruanweibiao
 *
 */
public class MessageInboxImpl implements MessageInbox {

	
	private CodecManager codecManager;
	
	@Inject
	public MessageInboxImpl(CodecManager codecManager) {
		this.codecManager = codecManager;
	}
	
	
	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.MessageInbox#receiveMsg(org.vsg.cusp.event.Message)
	 */
	@Override
	public void receiveMsg(Message<byte[]> msg) {
		
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
			

			
			//scheduleAndExecuteEvent(event);
			// --- arrange and execute object ---
			
			//Message<byte[]> respMsg = createResponseMsg(msg);

			
		}


	}

}
