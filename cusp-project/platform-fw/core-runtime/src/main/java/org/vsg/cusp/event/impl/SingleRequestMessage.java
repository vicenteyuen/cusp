package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.RequestMessageDecoder;
import org.vsg.cusp.eventbus.impl.CodecManager;
import org.vsg.cusp.eventbus.impl.codes.BooleanMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.BufferMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.ByteArrayMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.ByteMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.CharMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.DoubleMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.FloatMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.IntMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.LongMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.NullMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.ShortMessageCodec;
import org.vsg.cusp.eventbus.impl.codes.StringMessageCodec;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class SingleRequestMessage {
	
	public static final byte CODE_ID = 1;
	
	protected static CodecManager codecManager = new CodecManager();
	
	
	public SingleRequestMessage() {
		codecManager.registerCodec( new OperationEventMessageCodec() );
	}
	
	
	public CodecManager getCodecManager() {
		return codecManager;
	}
	
	private SingleRequestMessageDecoder reqMessageDecoder = new SingleRequestMessageDecoder();
	
	public RequestMessageDecoder getRequestManagerDecoder() {
		return reqMessageDecoder;
	}
	
	
	private static MessageCodec returnMsgCodecbySystemCodeId(byte systemCodeId) {
		MessageCodec  msgCodec = null;
		if ( NullMessageCodec.SYSTEMCODEC_ID == systemCodeId ) {
			msgCodec = codecManager.getCodec("null");
		}
		else if (ByteMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("byte");
		}
		else if (BooleanMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("byte");
		}		
		else if (ShortMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("short");
		}		
		else if (IntMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("int");
		}		
		else if (LongMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("long");
		}		
		else if (FloatMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("float");
		}		
		else if (CharMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("char");
		}
		else if (DoubleMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("double");
		}
		else if (StringMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("string");
		}
		else if (BufferMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("buffer");
		}			
		else if (ByteArrayMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("bytearray");
		}			
		else if (OperationEventMessageCodec.SYSTEMCODEC_ID == systemCodeId) {
			msgCodec = codecManager.getCodec("operation-event");
		}
		
		return msgCodec;
	}	

	/**
	 * 
	 * define single request message decoder 
	 *
	 */
	private class SingleRequestMessageDecoder implements RequestMessageDecoder {

		/* (non-Javadoc)
		 * @see org.vsg.cusp.event.RequestMessageDecoder#decode(byte[])
		 */
		@Override
		public Message<byte[]> decode(byte[] msgByteContByte) {
			
			int locFrom = 0;
			int locTo = locFrom + 1;
			byte systemCodeId = msgByteContByte[locFrom];
			
			MessageCodec messageCodec = returnMsgCodecbySystemCodeId(systemCodeId);
			System.out.println("code id : " + systemCodeId + " , " + messageCodec);
			// --- output content ---
			StringBuilder output = new StringBuilder();
			for (byte con : msgByteContByte) {
				output.append(con).append(" ");
			}
			
			byte compressByte = msgByteContByte[locTo];
			
			locFrom = locTo + 1;
			locTo = locFrom + Long.BYTES;
			byte[] contBytes = java.util.Arrays.copyOfRange(msgByteContByte, locFrom, locTo);
			long timestamp = Longs.fromByteArray( contBytes );

			
			locFrom = locTo;
			locTo = locFrom + Integer.BYTES;	
			contBytes = java.util.Arrays.copyOfRange(msgByteContByte, locFrom, locTo);
			int bodyLength = Ints.fromByteArray(contBytes);
			
			locFrom = locTo;
			locTo = locFrom + bodyLength;
			contBytes = java.util.Arrays.copyOfRange(msgByteContByte, locFrom, locTo);
			
			MessageImpl<byte[] , byte[]> msgImpl = new MessageImpl<byte[] , byte[]>();
			
			
			msgImpl.setMessageCodec(messageCodec);
			msgImpl.setSentBody( contBytes );

			return msgImpl;
		}


	}	
	
}
