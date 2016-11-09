package org.vsg.cusp.event;

public interface RequestMessageEncoder {

	<T> byte[] encode(Message<T> message);
	
}
