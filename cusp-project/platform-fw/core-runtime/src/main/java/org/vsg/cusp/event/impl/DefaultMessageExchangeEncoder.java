package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.ReqMessageModel;
import org.vsg.cusp.event.RequestMessageDecoder;
import org.vsg.cusp.event.RequestMessageEncoder;
import org.vsg.cusp.eventbus.impl.CodecManager;

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

		
		
		return totalBytes;
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub

		ReqMessageModel reqMsgModel = reqMsgSchemaEncoder.decode(msgBytes);
		
		// --- decode message type ---
		byte apiCodeId = reqMsgModel.getApiCodeId();

		Message msgImpl = null;
		if (reqMsg.getApiId() == apiCodeId) {
			RequestMessageDecoder reqMsgDecoder =  reqMsg.getRequestManagerDecoder();
			msgImpl = reqMsgDecoder.decode( reqMsgModel.getBody() );
		}

		
		return msgImpl;
	}
	
	

}
