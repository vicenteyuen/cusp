package org.vsg.cusp.eventbus.buffer.impl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

import org.vsg.cusp.eventbus.spi.Buffer;

public class BufferImpl implements Buffer {
	
	private ByteBuffer buffer;
	
	
	public BufferImpl() {
		buffer = ByteBuffer.allocate(4096);
	}
	
	public BufferImpl(byte[] bytes) {
		buffer = ByteBuffer.wrap( bytes );
	}
	
	public BufferImpl(String str) {
		this(str , "UTF-8");
	}
	
	public BufferImpl(String str , String enc) {
		this(str.getBytes(Charset.forName(Objects.requireNonNull(enc))));
	}
	
	public BufferImpl(int initialSizeHint) {
		buffer = ByteBuffer.allocate( initialSizeHint );
	}
	

	@Override
	public String toString(String enc) {
		String toString = null;
		try {
			toString = new String(buffer.array(), enc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toString;
	}

	@Override
	public String toString(Charset enc) {
		String toString = null;
		try {
			toString = new String(buffer.array(), enc.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toString;
	}

	@Override
	public byte getByte(int pos) {
		// TODO Auto-generated method stub
		return buffer.get(pos);
	}

	@Override
	public short getUnsignedByte(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int pos) {
		// TODO Auto-generated method stub
		return buffer.getInt(pos);
	}

	@Override
	public long getUnsignedInt(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(int pos) {
		// TODO Auto-generated method stub
		return buffer.getLong(pos);
	}

	@Override
	public double getDouble(int pos) {
		// TODO Auto-generated method stub
		return buffer.getDouble(pos);
	}

	@Override
	public float getFloat(int pos) {
		// TODO Auto-generated method stub
		return buffer.getFloat(pos);
	}

	@Override
	public short getShort(int pos) {
		// TODO Auto-generated method stub
		return buffer.getShort(pos);
	}

	@Override
	public int getUnsignedShort(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return buffer.array();
	}

	@Override
	public byte[] getBytes(int start, int end) {
		int length = end - start;
		
		byte[] dst = new byte[length];
		
		this.buffer.get(dst, start, length);
		return dst;
	}

	@Override
	public Buffer getBytes(byte[] dst) {
		this.buffer.wrap(dst);
		return this;
	}

	@Override
	public Buffer getBytes(byte[] dst, int dstIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer getBytes(int start, int end, byte[] dst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer getBytes(int start, int end, byte[] dst, int dstIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer getBuffer(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(int start, int end, String enc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendBuffer(Buffer buff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendBuffer(Buffer buff, int offset, int len) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendBytes(byte[] bytes) {
		// TODO Auto-generated method stub
		this.buffer.put( bytes );
		return this;
	}

	@Override
	public Buffer appendBytes(byte[] bytes, int offset, int len) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendByte(byte b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendUnsignedByte(short b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendInt(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendUnsignedInt(long i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendLong(long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendShort(short s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendUnsignedShort(int s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendFloat(float f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendDouble(double d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendString(String str, String enc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendString(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setByte(int pos, byte b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setUnsignedByte(int pos, short b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setInt(int pos, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setUnsignedInt(int pos, long i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setLong(int pos, long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setDouble(int pos, double d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setFloat(int pos, float f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setShort(int pos, short s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setUnsignedShort(int pos, int s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setBuffer(int pos, Buffer b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setBuffer(int pos, Buffer b, int offset, int len) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setBytes(int pos, ByteBuffer b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setBytes(int pos, byte[] b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setBytes(int pos, byte[] b, int offset, int len) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setString(int pos, String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer setString(int pos, String str, String enc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Buffer copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer slice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer slice(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

}
