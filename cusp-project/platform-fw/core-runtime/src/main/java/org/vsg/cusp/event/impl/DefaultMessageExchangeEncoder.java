package org.vsg.cusp.event.impl;

import java.io.UnsupportedEncodingException;

import org.vsg.cusp.core.utils.CommonUtils;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.ReqMessageModel;
import org.vsg.cusp.event.RequestMessageEncoder;
import org.vsg.cusp.eventbus.impl.CodecManager;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

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
		
		byte[] headerBytes = Bytes.concat(
				new byte[]{reqMsgModel.getApiCodeId()},
				Shorts.toByteArray(reqMsgModel.getVersion()),
				Longs.toByteArray( reqMsgModel.getCorrelationId()),
				reqMsgModel.getClientMac()
			);
		
		byte[] totalBytes = headerBytes;

		
		long totalLength = totalBytes.length;
		
		
		String uid = CommonUtils.getUid( reqMsgModel.getClientMac() );
		System.out.println(uid);
		// --- paurse corrent id array ---
		String[] correlationIds = uid.split("\\.");
		byte[] correlationIdsPrefix =Longs.toByteArray( Long.parseLong( correlationIds[0] ) ) ;
		byte[] correlationIdsSuffix =Longs.toByteArray( Long.parseLong( correlationIds[1] ) ) ;
		byte[] correlationIdsSeq = Ints.toByteArray( 0 );

		

		
		byte[] all = Bytes.concat( 
				Longs.toByteArray( totalLength ) ,
				new byte[]{Message.TYPE_REQ}, 
				reqMsgModel.getClientMac(),
				Longs.toByteArray( System.currentTimeMillis() ),
				correlationIdsPrefix,
				correlationIdsSuffix,
				correlationIdsSeq,
				totalBytes);
		
		
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
		
		// --- get clinet mac ---
		locFrom = locTo;
		locTo = locFrom + 6;
		try {
			msg.headers().add("PUBLISHER", new String(java.util.Arrays.copyOfRange(inputContent, locFrom, locTo),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		locFrom = locTo;
		locTo = locFrom + Long.BYTES;		
		long publisherSentTime = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );

		locFrom = locTo;
		locTo = locFrom + Long.BYTES;
		long corrIdPrefix = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		
		locFrom = locTo;
		locTo = locFrom + Long.BYTES;
		long corrIdSubfix = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		
		locFrom = locTo;
		locTo = locFrom + Integer.BYTES;
		int corrIdSeq = Ints.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );

		

		
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
