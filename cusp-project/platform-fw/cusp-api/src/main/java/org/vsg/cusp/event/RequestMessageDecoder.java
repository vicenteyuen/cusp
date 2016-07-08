package org.vsg.cusp.event;

public interface RequestMessageDecoder {
	
	/**
	 * parse message content for byte value
	 * @param msgByteContent
	 * @return
	 */
	<T> Message<T> decode(byte[] msgByteContent);	

}
