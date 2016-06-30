package org.vsg.cusp.eventbus.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.net.SocketException;
import java.net.UnknownHostException;

import org.vsg.cusp.eventbus.Message;
import org.vsg.cusp.eventbus.MessageCodec;

public class SimpleMsgExchangeProtocol implements MsgExchangeProtocol {

	public SimpleMsgExchangeProtocol() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode(MessageImpl msg) {
		

		
		MessageCodec mc = msg.getMessageCodec();
		
		// --- encode content ---
		int contentTotalLenght = 0;
		
		
		try {
			byte[] cliendAddressIdBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			
			System.out.println("size : " + cliendAddressIdBytes.length);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// --- build message bytes ----
		// attribute field ---
		byte systemCodeId = msg.getMessageCodec().systemCodecID();
		byte[] systemCodeIdByte = new byte[]{systemCodeId};
		
		// --- check body content ---
		Object body = msg.body();
		
		//byte[] valueBytes = msg.body();



		return null;
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
