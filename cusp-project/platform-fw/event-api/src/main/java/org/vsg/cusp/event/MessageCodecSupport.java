package org.vsg.cusp.event;


public interface MessageCodecSupport {
	
	<S,R> MessageCodec<S,R> getMessageCodec();

}
