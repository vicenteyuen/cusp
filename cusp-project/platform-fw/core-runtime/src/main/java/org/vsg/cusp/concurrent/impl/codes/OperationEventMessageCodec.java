/**
 * 
 */
package org.vsg.cusp.concurrent.impl.codes;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.eventbus.spi.Buffer;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventMessageCodec implements MessageCodec<OperationEvent, byte[]> {
	
	public static final byte SYSTEMCODEC_ID = 12;
	

	@Override
	public void encodeToWire(Buffer buffer, OperationEvent s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] decodeFromWire(int pos, Buffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] transform(OperationEvent s) {
	
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
		
		return resultByte;
	}
	
	
	private StringBuilder convertToString(Method method) {
		String methodName = method.getName();
		Class<?>[] paramTypeCls = method.getParameterTypes();
		
		StringBuilder result = new StringBuilder(methodName);
		for (Class<?> oneCls : paramTypeCls) {
			result.append(",");
			result.append(oneCls.getName());		
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
		return "operation-event";
	}

	@Override
	public byte systemCodecID() {
		// TODO Auto-generated method stub
		return SYSTEMCODEC_ID;
	}

	
	
}
