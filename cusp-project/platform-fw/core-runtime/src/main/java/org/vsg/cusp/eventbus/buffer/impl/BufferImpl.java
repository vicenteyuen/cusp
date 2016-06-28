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
		return (short) (getByte(pos) & 0xff);
	}

	@Override
	public int getInt(int pos) {
		// TODO Auto-generated method stub
		return buffer.getInt(pos);
	}

	@Override
	public long getUnsignedInt(int pos) {

		return getInt(pos) & 0x0FFFFFFFF;
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
		return getShort(pos) & 0x0FFFF;
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
		
		buffer.get(dst, dstIndex, buffer.array().length - dstIndex);
		return this;
	}

	@Override
	public Buffer getBytes(int start, int end, byte[] dst) {
		buffer.get(dst, start, end - start);
		return this;
	}

	@Override
	public Buffer getBytes(int start, int end, byte[] dst, int dstIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer getBuffer(int start, int end) {
		// copy buffer
		byte[] dst = new byte[end-start];
		buffer.get(dst, start, end-start);
		
		Buffer buffer = new BufferImpl(dst);
		
		return buffer;
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
		this.appendBytes( buff.getBytes() );
		return this;
	}

	@Override
	public Buffer appendBuffer(Buffer buff, int offset, int len) {
		buffer.put(buff.getBytes(), offset, len);
		return this;
	}

	@Override
	public Buffer appendBytes(byte[] bytes) {
		// TODO Auto-generated method stub
		this.buffer.put( bytes );
		return this;
	}

	@Override
	public Buffer appendBytes(byte[] bytes, int offset, int len) {
		buffer.put(bytes, offset, len);
		return this;
	}

	@Override
	public Buffer appendByte(byte b) {
		buffer.put(b);
		return this;
	}

	@Override
	public Buffer appendUnsignedByte(short b) {
		// TODO Auto-generated method stub
		buffer.putShort((short) (b & 0xff));
		return this;
	}

	@Override
	public Buffer appendInt(int i) {
		buffer.putInt(i);
		return this;
	}

	@Override
	public Buffer appendUnsignedInt(long i) {
		buffer.putLong( i & 0x0FFFFFFFF );
		return this;
	}

	@Override
	public Buffer appendLong(long l) {
		buffer.putLong(l);
		return this;
	}

	@Override
	public Buffer appendShort(short s) {
		buffer.putShort(s);
		return this;
	}

	@Override
	public Buffer appendUnsignedShort(int s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buffer appendFloat(float f) {
		buffer.putFloat(f);
		return this;
	}

	@Override
	public Buffer appendDouble(double d) {
		buffer.putDouble(d);
		return this;
	}

	@Override
	public Buffer appendString(String str, String enc) {
		try {
			buffer.put( str.getBytes(enc) );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public Buffer appendString(String str) {
		buffer.put( str.getBytes() );
		return this;
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
		return buffer.array().length;
	}

	@Override
	public Buffer copy() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Buffer slice() {
		buffer.slice();
		return this;
	}

	@Override
	public Buffer slice(int start, int end) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
