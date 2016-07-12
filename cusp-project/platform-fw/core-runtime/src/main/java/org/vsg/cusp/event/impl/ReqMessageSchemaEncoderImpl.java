package org.vsg.cusp.event.impl;

import org.vsg.cusp.event.ReqMessageModel;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

public class ReqMessageSchemaEncoderImpl implements ReqMessageSchemaDecoder , ReqMessageSchemaEncoder {
	

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
		
		
		// --- get client mac ---
		locFrom = locTo;
		locTo = locFrom + 6;
		byte[] clientMac = java.util.Arrays.copyOfRange(inputContent, locFrom, locTo);
		
		locFrom = locTo;
		int bodyLength = inputContent.length-locTo;
		byte[] bodyContent = java.util.Arrays.copyOfRange(inputContent, locFrom, bodyLength);
		
		parseToModel(bodyContent , model);

		return model;
	}
	
	
	private void parseToModel(byte[] bodyContent , ReqMessageModel model) {
		// --- get the offset ---
		int locFrom = 0;
		int locTo = locFrom + Longs.BYTES;
		byte[] contBytes = java.util.Arrays.copyOfRange(bodyContent, locFrom, locTo);
		long offset = Longs.fromByteArray( contBytes );
		
		locFrom = locTo;
		locTo = locFrom + Ints.BYTES;
		contBytes = java.util.Arrays.copyOfRange(bodyContent, locFrom, locTo);
		int length = Ints.fromByteArray(contBytes);
		
		int contentLocFrom = locTo + (int)offset;
		int contentLocTo = contentLocFrom + length;
		contBytes = java.util.Arrays.copyOfRange(bodyContent, contentLocFrom, contentLocTo);

		model.setBody(contBytes);
	}


	@Override
	public byte[] encode(ReqMessageModel model) {
		
		int addLength = model.getAddress().length();
		StringBuilder addLenSb = new StringBuilder(model.getAddress());
		if (addLength < 15) {
			for (int i = 0 ; i < 15 - addLength ; i++) {
				addLenSb.append("");
			}
		}
		
		System.out.println(addLenSb.length());

		
		byte[] headerBytes = Bytes.concat(
			new byte[]{model.getApiCodeId()},
			Shorts.toByteArray(model.getVersion()),
			Longs.toByteArray( model.getCorrelationId()),
			model.getClientMac()
		);

		
		byte[] bodyBytes = model.getBody();
		return Bytes.concat( headerBytes, bodyBytes );
	}


	@Override
	public ReqMessageModel genFromBodyContent(byte[] bodyContent,
			RequestMessage requestMessage) {
		ReqMessageModel model = new ReqMessageModel();
		model.setApiCodeId( requestMessage.getApiId() );
		model.setVersion( requestMessage.getApiVersion() );
		model.setCorrelationId( requestMessage.getCorrelationId() );
		model.setClientMac( requestMessage.getClientAddress() );
		//model.setAddress( reque );
		// --- add client address ---

		model.setBody( bodyContent );
		return model;
	}
	
	

}
