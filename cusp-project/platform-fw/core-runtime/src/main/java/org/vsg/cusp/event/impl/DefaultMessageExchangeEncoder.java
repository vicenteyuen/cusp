package org.vsg.cusp.event.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.vsg.cusp.core.utils.CorrelationIdGenerator;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.MessageCodecSupport;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;
import org.vsg.cusp.eventbus.impl.SimpleMessageRequestPack;
import org.zeromq.ZMQ;

import com.google.common.primitives.Bytes;

public class DefaultMessageExchangeEncoder implements MessageExchangeEncoder {
	
	

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
	
		return Bytes.concat( headerBytes, bodyBytes );
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub
		System.out.println("Received2 " + new String (msgBytes, ZMQ.CHARSET) );		
		return null;
	}
	
	

}
