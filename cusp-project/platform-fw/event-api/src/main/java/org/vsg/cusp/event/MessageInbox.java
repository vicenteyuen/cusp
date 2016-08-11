package org.vsg.cusp.event;

public interface MessageInbox {
	
	/**
	 * receive message to inbox
	 */
	void receiveMsg(Message<byte[]> msg );

}
