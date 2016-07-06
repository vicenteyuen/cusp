/**
 * 
 */
package org.vsg.cusp.concurrent.impl.codes;

import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventMessageCodec implements MessageCodec<OperationEvent, OperationEvent> {
	
	public static final byte SYSTEMCODEC_ID = 12;
	

	@Override
	public void encodeToWire(Buffer buffer, OperationEvent s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OperationEvent decodeFromWire(int pos, Buffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationEvent transform(OperationEvent s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "operation-event";
	}

	@Override
	public byte systemCodecID() {
		// TODO Auto-generated method stub
		return SYSTEMCODEC_ID;
	}

	
	
}
