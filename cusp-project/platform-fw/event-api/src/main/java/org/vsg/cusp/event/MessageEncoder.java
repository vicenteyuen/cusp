package org.vsg.cusp.event;

/**
 * This class is used by encode/decode message object.
 * @author Vicente Yuen
 */
public interface MessageEncoder {
	
	/**
	 * 
	 * @param msg support any class to covert 
	 * @return
	 */
	byte[] encode(Message<byte[]> msg);
	
	
	/**
	 * convert message instance for msg byte handle
	 * @param msgBytes
	 * @return
	 */
	Message<byte[]> decode(byte[] msgBytes);

}
