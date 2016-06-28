package org.vsg.cusp.eventbus.impl.codes;

import org.vsg.cusp.eventbus.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

public class CharMessageCodec implements MessageCodec<Character, Character> {

	  @Override
	  public void encodeToWire(Buffer buffer, Character chr) {
	    buffer.appendShort((short)chr.charValue());
	  }

	  @Override
	  public Character decodeFromWire(int pos, Buffer buffer) {
	    return (char)buffer.getShort(pos);
	  }

	  @Override
	  public Character transform(Character c) {
	    // Characters are immutable so just return it
	    return c;
	  }

	  @Override
	  public String name() {
	    return "char";
	  }

	  @Override
	  public byte systemCodecID() {
	    return 10;
	  }

}
