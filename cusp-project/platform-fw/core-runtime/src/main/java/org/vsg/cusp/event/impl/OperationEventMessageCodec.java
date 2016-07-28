/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventMessageCodec implements MessageCodec<OperationEvent, OperationEvent> {
	
	public static final byte SYSTEMCODEC_ID = -1;
	
	public static final String NAME = "operation-event";

	@Override
	public void encodeToWire(Buffer buffer, OperationEvent s) {
		
		buffer
			.appendString( s.assoClassName() )
			.appendString(":")
			.appendString( s.getEventId())
			;
		buffer.appendString( "|" );
		
		//buffer.appendString( "|" );
		buffer.appendString( convertToString(s.assoBindMethod()).toString() );

		
	}

	@Override
	public OperationEvent decodeFromWire(int pos, Buffer buffer) {
		StringBuilder content = new StringBuilder();

		

		StringTokenizer st = new StringTokenizer( new String(buffer.getBytes()) , "\\|" );
		String eventId = st.nextToken();
		
		String methodName = st.nextToken();

		
		System.out.println( eventId);
		System.out.println(methodName);
		
		return null;
	}

	@Override
	public OperationEvent transform(OperationEvent s) {
		/*
	
		String clsName = s.assoClassName();
		
		String eventId = s.getEventId();
		
		StringBuilder result = new StringBuilder("{");
		result.append(clsName).append("=").append(eventId).append("|");

		StringBuilder methodString = convertToString(s.assoBindMethod());
		result.append(methodString).append("|");
		
		StringBuilder argmentString = convertToString(s.getRuntimeArgument());
		result.append(argmentString);
		
		result.append("}");
		
		byte[] resultByte = result.toString().getBytes(Charset.forName("UTF-8"));
		*/
		
		return s;
	}
	
	
	private StringBuilder convertToString(Method method) {
		String methodName = method.getName();
		Class<?>[] paramTypeCls = method.getParameterTypes();
		
		StringBuilder result = new StringBuilder(methodName);
		result.append(":");
		
		// --- return  parameter ---
		StringBuilder paramTypeStr = new StringBuilder();
		for (Class<?> oneCls : paramTypeCls) {
			if (paramTypeStr.length() > 0) {
				paramTypeStr.append(",");
			}
			paramTypeStr.append(oneCls.getName());		
		}
		
		if (paramTypeStr.length() == 0) {
			result.append("void");
		} else {
			result.append(paramTypeStr);
		}
		
		return result;
	}
	
	private StringBuilder convertToString(Serializable[] arguments) {
		StringBuilder argmentSb = new StringBuilder();
		if (arguments == null || arguments.length == 0 ) {
			return argmentSb;
		}
		for (Serializable arg : arguments) {
			if (argmentSb.length() > 0) {
				argmentSb.append(",");
			}
			argmentSb.append(arg.toString());
		}
		return argmentSb;
	}
	

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return NAME;
	}

	@Override
	public byte systemCodecID() {
		// TODO Auto-generated method stub
		return SYSTEMCODEC_ID;
	}

	
	
}
