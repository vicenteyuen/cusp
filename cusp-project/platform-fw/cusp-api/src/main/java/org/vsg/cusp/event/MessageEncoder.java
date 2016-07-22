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
	byte[] encode(Message<?> msg);
	
	
	/**
	 * convert message instance for msg byte handle
	 * @param msgBytes
	 * @return
	 */
	Message<?> decode(byte[] msgBytes);

}
