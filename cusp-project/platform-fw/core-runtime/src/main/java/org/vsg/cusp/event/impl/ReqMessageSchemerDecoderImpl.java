package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.ReqMessageModel;

import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

public class ReqMessageSchemerDecoderImpl implements ReqMessageSchemerDecoder {

	@Override
	public ReqMessageModel decode(byte[] inputContent) {
		ReqMessageModel model = new ReqMessageModel();
		short index = 0;
		model.setApiCodeId( inputContent[index++] );
		
		// --- get version content ---
		int locFrom = index++;
		int locTo = locFrom + Short.BYTES;
		short version = Shorts.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		model.setVersion( version );
		
		// --- get correlationId ---
		locFrom = locTo;
		locTo = locFrom + Longs.BYTES;
		long correlationId = Longs.fromByteArray( java.util.Arrays.copyOfRange(inputContent, locFrom, locTo) );
		model.setCorrelationId( correlationId );


		return model;
	}

}
