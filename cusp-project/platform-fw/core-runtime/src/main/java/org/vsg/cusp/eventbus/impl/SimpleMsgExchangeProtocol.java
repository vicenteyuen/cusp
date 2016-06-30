package org.vsg.cusp.eventbus.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;

import org.vsg.cusp.eventbus.Message;

public class SimpleMsgExchangeProtocol implements MsgExchangeProtocol {

	public SimpleMsgExchangeProtocol() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] encode(MessageImpl msg) {
		
		byte systemCodeId = msg.getMessageCodec().systemCodecID();
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		
		
		//NetworkInterface.getByIndex(index)



		return null;
	}

	@Override
	public Message decode(byte[] msgBytes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
