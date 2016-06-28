package org.vsg.cusp.eventbus.spi;


public interface BufferFactory {

	Buffer buffer(int initialSizeHint);

	Buffer buffer();
	
	Buffer buffer(String str);

	Buffer buffer(String str, String enc);

	Buffer buffer(byte[] bytes);	
	
}
