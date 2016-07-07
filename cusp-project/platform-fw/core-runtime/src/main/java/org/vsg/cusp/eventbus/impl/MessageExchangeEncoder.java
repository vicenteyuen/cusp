package org.vsg.cusp.eventbus.impl;

import org.vsg.cusp.event.Message;

public interface MessageExchangeEncoder {
	
	
	byte[] encode(Message msg);
	
	
	Message decode(byte[] msgBytes);

}
