package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.ReqMessageModel;
import org.vsg.cusp.event.RequestMessageEncoder;
import org.vsg.cusp.eventbus.impl.CodecManager;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;

/**
 * Define Message Encoder default instance 
 * @author Vicente Yuen
 *
 */
public class DefaultMessageExchangeEncoder implements MessageEncoder {
	
	protected CodecManager codecManager = new CodecManager();
	
	
	public SingleRequestMessage reqMsg = new SingleRequestMessage();
	
	private ReqMessageSchemaEncoderImpl reqMsgSchemaEncoder = new ReqMessageSchemaEncoderImpl();


	@Override
	public byte[] encode(Message msg) {

		
		RequestMessageEncoder reqMsgEncoder =  reqMsg.getRequestMessageEncoder();
		
		byte[] body = reqMsgEncoder.encode((Message<byte[]>)msg);
		
		ReqMessageModel  reqMsgModel = reqMsgSchemaEncoder.genFromBodyContent(body, reqMsg);
		byte[] totalBytes = reqMsgSchemaEncoder.encode( reqMsgModel );

		
		long totalLength = totalBytes.length;
		
		
		
		byte[] all = Bytes.concat( Longs.toByteArray( totalLength ) , new byte[]{Message.TYPE_REQ} , totalBytes);
		
		
		return all;
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub
		ByteArrayMessageImpl _inst = new ByteArrayMessageImpl();
		
		// --- parse message header ---
		parseMessageHeader(msgBytes, (AbstractMessage<byte[]>) _inst);
		
		/*
		ReqMessageModel reqMsgModel = reqMsgSchemaEncoder.decode(msgBytes);
		
		// --- decode message type ---
		byte apiCodeId = reqMsgModel.getApiCodeId();


		if (reqMsg.getApiId() == apiCodeId) {
			RequestMessageDecoder reqMsgDecoder =  reqMsg.getRequestManagerDecoder();
			_inst = reqMsgDecoder.decode( reqMsgModel.getBody() );
		}
		*/

		
		return _inst;
	}
	
	
	private void parseMessageHeader(byte[] inputContent , AbstractMessage<byte[]> msg) {
		int locFrom = 0;
		int locTo = locFrom + Long.BYTES;
		
		long msgTotalLength = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		msg.setHeadPos( locTo );
		
		locFrom = locTo;
		locTo = locFrom + 1;
		
		byte msgType = java.util.Arrays.copyOfRange(inputContent, locFrom, locTo)[0];
		msg.setMsgType( msgType );
		
		

		

		/*
		ReqMessageModel model = new ReqMessageModel();
		short index = 0;
		model.setApiCodeId( inputContent[index++] );
		
		// --- get version content ---
		int locFrom = index++;
		int locTo = locFrom + Short.BYTES;
		
		short version = Shorts.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		model.setVersion( version );
	
		// --- get correlationId ---
		locFrom = locTo;
		locTo = locFrom + Longs.BYTES;
		long correlationId = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		model.setCorrelationId( correlationId );

		
		// --- get client mac ---
		locFrom = locTo;
		locTo = locFrom + 6;
		byte[] clientMac = java.util.Arrays.copyOfRange(inputContent, locFrom, locTo);
		model.setClientMac(clientMac);
		
		StringBuilder output = new StringBuilder();
		for (byte con : clientMac) {
			output.append(con).append(" ");
		}
		locFrom = locTo;
		int bodyLength = inputContent.length;
		byte[] bodyContent = java.util.Arrays.copyOfRange(inputContent, locFrom, bodyLength);
		*/		
	}
	
	
	

}
