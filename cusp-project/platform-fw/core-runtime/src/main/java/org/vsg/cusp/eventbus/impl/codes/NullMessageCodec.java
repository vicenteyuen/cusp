package org.vsg.cusp.eventbus.impl.codes;

import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

public class NullMessageCodec implements MessageCodec<String, String> {

	  @Override
	  public void encodeToWire(Buffer buffer, String s) {
	  }

	  @Override
	  public String decodeFromWire(int pos, Buffer buffer) {
	    return null;
	  }

	  @Override
	  public String transform(String s) {
	    return null;
	  }

	  @Override
	  public String name() {
	    return "null";
	  }

	  @Override
	  public byte systemCodecID() {
	    return 0;
	  }

}
