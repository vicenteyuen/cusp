package org.vsg.cusp.eventbus.impl;

public interface MessageRequestPack {
	
	
	byte[] headerPack();
	
	/**
	 * add one message pack to byte
	 * @param msgBody
	 * @return
	 */
	byte[] addMessageBody(Object msgBody);
	
	byte[] messagePack();
	

}
