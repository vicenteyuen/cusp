package org.vsg.cusp.event;

public interface MessageAware<T> {
	
	
	void setAddress(String address);
	
	void setBody(T body);
	
	void setMsgType(byte msgType);
	

}
