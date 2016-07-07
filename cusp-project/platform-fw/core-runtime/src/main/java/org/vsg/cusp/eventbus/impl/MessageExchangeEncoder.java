package org.vsg.cusp.eventbus.impl;

import org.vsg.cusp.event.Message;

public interface MessageExchangeEncoder {
	
	
	byte[] encode(MessageImpl msg);
	
	
	Message decode(byte[] msgBytes);

}
