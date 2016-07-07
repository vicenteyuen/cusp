package org.vsg.cusp.eventbus.impl.codes;

import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

public class BooleanMessageCodec implements MessageCodec<Boolean, Boolean> {

	public static final byte SYSTEMCODEC_ID = 2;

	@Override
	public void encodeToWire(Buffer buffer, Boolean s) {
		buffer.appendByte((byte) (s ? 0 : 1));
	}

	@Override
	public Boolean decodeFromWire(int pos, Buffer buffer) {
		return buffer.getByte(pos) == 0;
	}

	@Override
	public Boolean transform(Boolean s) {
		// TODO Auto-generated method stub
		return s;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "boolean";
	}

	@Override
	public byte systemCodecID() {
		// TODO Auto-generated method stub
		return SYSTEMCODEC_ID;
	}

}
