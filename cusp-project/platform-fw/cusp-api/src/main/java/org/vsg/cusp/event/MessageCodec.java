package org.vsg.cusp.event;

import org.vsg.cusp.eventbus.spi.Buffer;


public interface MessageCodec<S, R> {

	void encodeToWire(Buffer buffer, S s);
	
	R decodeFromWire(int pos , Buffer buffer);

	/**
	 * If a message is sent <i>locally</i> across the event bus, this method is
	 * called to transform the message from the sent type S to the received type
	 * R
	 *
	 * @param s
	 *            the sent message
	 * @return the transformed message
	 */
	R transform(S s);

	/**
	 * The codec name. Each codec must have a unique name. This is used to
	 * identify a codec when sending a message and for unregistering codecs.
	 *
	 * @return the name
	 */
	String name();

	/**
	 * Used to identify system codecs. Should always return -1 for a user codec.
	 *
	 * @return -1 for a user codec.
	 */
	byte systemCodecID();

}
