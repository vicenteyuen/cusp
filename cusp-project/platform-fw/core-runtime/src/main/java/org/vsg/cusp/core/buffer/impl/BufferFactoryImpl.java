package org.vsg.cusp.core.buffer.impl;

import org.vsg.cusp.core.Buffer;
import org.vsg.cusp.core.BufferFactory;

public class BufferFactoryImpl implements BufferFactory {

	@Override
	public Buffer buffer(int initialSizeHint) {
		// TODO Auto-generated method stub
		return new BufferImpl(initialSizeHint);
	}

	@Override
	public Buffer buffer() {
		// TODO Auto-generated method stub
		return new BufferImpl();
	}

	@Override
	public Buffer buffer(String str) {
		// TODO Auto-generated method stub
		return new BufferImpl(str);
	}

	@Override
	public Buffer buffer(String str, String enc) {
		// TODO Auto-generated method stub
		return new BufferImpl(str , enc);
	}

	@Override
	public Buffer buffer(byte[] bytes) {
		// TODO Auto-generated method stub
		return new BufferImpl(bytes);
	}

}
