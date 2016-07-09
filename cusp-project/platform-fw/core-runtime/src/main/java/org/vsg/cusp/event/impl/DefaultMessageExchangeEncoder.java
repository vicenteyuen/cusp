package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.ReqMessageModel;
import org.vsg.cusp.event.RequestMessageDecoder;
import org.vsg.cusp.event.RequestMessageEncoder;
import org.vsg.cusp.eventbus.impl.CodecManager;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;

public class DefaultMessageExchangeEncoder implements MessageExchangeEncoder {
	
	protected CodecManager codecManager = new CodecManager();
	
	
	public SingleRequestMessage reqMsg = new SingleRequestMessage();
	
	private ReqMessageSchemaEncoderImpl reqMsgSchemaEncoder = new ReqMessageSchemaEncoderImpl();
	

	@Override
	public byte[] encode(Message msg) {
		RequestMessageEncoder reqMsgEncoder =  reqMsg.getRequestMessageEncoder();
		byte[] body = reqMsgEncoder.encode(msg);
		
		ReqMessageModel  reqMsgModel = reqMsgSchemaEncoder.genFromBodyContent(body, reqMsg);
		return reqMsgSchemaEncoder.encode( reqMsgModel );
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
		
		System.out.println(msgImpl);



		
		return msgImpl;
	}
	
	

}
