package org.vsg.cusp.eventbus.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.vsg.cusp.core.utils.CorrelationIdGenerator;
import org.vsg.cusp.eventbus.Message;
import org.vsg.cusp.eventbus.MessageCodec;

import com.google.common.primitives.Bytes;

public class SimpleMsgExchangeProtocol implements MsgExchangeProtocol {

	public SimpleMsgExchangeProtocol() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	@Override
	public byte[] encode(MessageImpl msg) {
		

		
		MessageCodec mc = msg.getMessageCodec();
		
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
		return null;
	}
	
	

}
