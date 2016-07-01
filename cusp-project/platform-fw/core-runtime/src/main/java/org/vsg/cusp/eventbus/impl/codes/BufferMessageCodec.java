package org.vsg.cusp.eventbus.impl.codes;

import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

public class BufferMessageCodec implements MessageCodec<Buffer, Buffer> {

	public static final byte SYSTEMCODEC_ID = 10;

	@Override
	public void encodeToWire(Buffer buffer, Buffer b) {
		buffer.appendInt(b.length());
		buffer.appendBuffer(b);
	}

	@Override
	public Buffer decodeFromWire(int pos, Buffer buffer) {
		int length = buffer.getInt(pos);
		pos += 4;
		return buffer.getBuffer(pos, pos + length);

	}

	@Override
	public Buffer transform(Buffer b) {
		return b.copy();
	}

	@Override
	public String name() {
		return "buffer";
	}

	@Override
	public byte systemCodecID() {
		return SYSTEMCODEC_ID;
	}

}
