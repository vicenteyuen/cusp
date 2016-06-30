package org.vsg.cusp.eventbus.impl;

import org.vsg.cusp.eventbus.Message;

public interface MsgExchangeProtocol {
	
	
	byte[] encode(MessageImpl msg);
	
	
	Message decode(byte[] msgBytes);

}
