/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.Vector;

import org.vsg.cusp.core.Buffer;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.OperationEvent;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventMessageCodec implements MessageCodec<OperationEvent, OperationEvent> {
	
	public static final byte SYSTEMCODEC_ID = -1;
	
	public static final String NAME = "operation-event";

	@Override
	public void encodeToWire(Buffer buffer, OperationEvent s) {
		/*
		buffer
			.appendString( s.assoClassName() )
			.appendString(":")
			.appendString( s.getEventName())
			;
		buffer.appendString( "|" );
		buffer.appendString( convertToString(s.assoBindMethod()).toString() );

		StringBuilder argmentString = convertToString(s.getRuntimeArgument());
		if (argmentString.length() > 0) {
			buffer.appendString( "|" );
		}
		buffer.appendString(argmentString.toString());
		*/
	}

	@Override
	public OperationEvent decodeFromWire(int pos, Buffer buffer) {
	
		
		StringTokenizer st = new StringTokenizer( new String(buffer.getBytes()) , "\\|" );
		
		OperationEventImpl oeImpl = new OperationEventImpl();
		
		String classAndEventId = st.nextToken();
		String[] clsAndEvtIds = classAndEventId.split(":");
		//oeImpl.setAssoClassName( clsAndEvtIds[0] );
		//oeImpl.setEventId( clsAndEvtIds[1] );
		
		String methodName = st.nextToken();
		/*
		String[] methodAndParamTypes = methodName.split(":");
		Method method = parseToMethod(oeImpl.assoClassName() , methodAndParamTypes[0] , methodAndParamTypes[1].split(","));
		

		if (st.hasMoreTokens()) {
			String params = st.nextToken();
			String[] paramArray = params.split(",");
			
			Collection<java.io.Serializable> paramsColl = new Vector<java.io.Serializable>();
			for (String singleParam : paramArray) {
				paramsColl.add( singleParam );
			}
			Serializable[]  arguments = paramsColl.toArray(new java.io.Serializable[0]);
			oeImpl.setRuntimeArgument( arguments );
		}
		*/
		
		return oeImpl;
	}
	
	private Method parseToMethod(String clsName , String methodName , String... paramTypeNames) {
		Method method = null;
		try {
			Class cls = Thread.currentThread().getContextClassLoader().loadClass(clsName);
			
			Class[] methodClasses = new Class[paramTypeNames.length];
			
			Class methodCls = null;
			int i = 0;
			for (String paramTypeName : paramTypeNames) {
				methodCls = Thread.currentThread().getContextClassLoader().loadClass(paramTypeName);
				if (null != methodClasses) {
					methodClasses[i++] = methodCls;
				}
			}

			method = cls.getMethod(methodName , methodClasses);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return method;
		
		
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
