package org.vsg.cusp.eventbus.impl.codes;

import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

public class ByteArrayMessageCodec implements MessageCodec<byte[], byte[]> {

	  @Override
	  public void encodeToWire(Buffer buffer, byte[] byteArray) {
	    buffer.appendInt(byteArray.length);
	    buffer.appendBytes(byteArray);
	  }

	  @Override
	  public byte[] decodeFromWire(int pos, Buffer buffer) {
	    int length = buffer.getInt(pos);
	    pos += 4;
	    return buffer.getBytes(pos, pos + length);
	  }

	  @Override
	  public byte[] transform(byte[] bytes) {
	    byte[] copied = new byte[bytes.length];
	    System.arraycopy(bytes, 0, copied, 0, bytes.length);
	    return copied;
	  }

	  @Override
	  public String name() {
	    return "bytearray";
	  }

	  @Override
	  public byte systemCodecID() {
	    return 11;
	  }

}
