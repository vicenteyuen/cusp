package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.ReqMessageModel;

public interface ReqMessageSchemerDecoder {

	ReqMessageModel decode(byte[] inputContent);	
	
}
