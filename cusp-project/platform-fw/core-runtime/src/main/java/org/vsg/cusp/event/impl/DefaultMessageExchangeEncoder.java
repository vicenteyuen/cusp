package org.vsg.cusp.event.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.vsg.cusp.core.utils.CorrelationIdGenerator;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.MessageCodecSupport;
import org.vsg.cusp.event.ReqMessageModel;
import org.vsg.cusp.event.RequestMessageDecoder;
import org.vsg.cusp.eventbus.impl.CodecManager;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;

import com.google.common.primitives.Bytes;

public class DefaultMessageExchangeEncoder implements MessageExchangeEncoder {
	
	protected CodecManager codecManager = new CodecManager();

	@Override
	public byte[] encode(Message msg) {
		
		MessageCodec mc = null;
		if (msg instanceof MessageCodecSupport) {
			MessageCodecSupport mcs = (MessageCodecSupport)msg;
			mc = mcs.getMessageCodec();
		}
		
		// --- encode content ---
		int contentTotalLenght = 0;
		
		CorrelationIdGenerator inst = null;
		byte[] cliendAddressIdBytes = null;
		try {
			cliendAddressIdBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			inst = CorrelationIdGenerator.genInstance(cliendAddressIdBytes);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- build message bytes ----
		SimpleMessageRequestPack mp = new SimpleMessageRequestPack();
		mp.setMesCodes(mc);
		mp.setCorrelationIdGenerator(inst);
		mp.setClientMac( cliendAddressIdBytes );
		
		mp.addMessageBody( msg.body() );
		
		byte[] headerBytes = mp.headerPack();

		byte[] bodyBytes = mp.messagePack();
	
		return Bytes.concat(headerBytes , bodyBytes);
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub
		ReqMessageSchemaDecoderImpl decoder = new ReqMessageSchemaDecoderImpl();
		ReqMessageModel reqMsgModel = decoder.decode(msgBytes);
		
		// --- decode message type ---
		byte apiCodeId = reqMsgModel.getApiCodeId();
		
		Message msgImpl = null;
		if (SingleRequestMessage.CODE_ID == apiCodeId) {
			SingleRequestMessage reqMsg = new SingleRequestMessage();
			RequestMessageDecoder reqMsgDecoder =  reqMsg.getRequestManagerDecoder();

			msgImpl = reqMsgDecoder.decode( reqMsgModel.getBody() );
		}
		
		System.out.println(msgImpl);



		
		return msgImpl;
	}
	
	

}
