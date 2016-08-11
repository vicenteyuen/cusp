/**
 * 
 */
package org.vsg.cusp.event.impl;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.StringTokenizer;

import org.vsg.cusp.core.Buffer;
import org.vsg.cusp.event.EventMethodDescription;
import org.vsg.cusp.event.MessageCodec;
import org.vsg.cusp.event.OperationEvent;
import org.vsg.cusp.event.RuntimeParam;

import com.google.common.primitives.Bytes;

/**
 * @author Vicente Yuen
 *
 */
public class OperationEventMessageCodec implements MessageCodec<OperationEvent, OperationEvent> {
	
	public static final byte SYSTEMCODEC_ID = -1;
	
	public static final String NAME = "operation-event";

	@Override
	public void encodeToWire(Buffer buffer, OperationEvent s) {
		
		EventMethodDescription  evtMethodDesc = s.getMethodDescription();
		
		/**
		 * set the event name
		 */
		buffer.appendString( evtMethodDesc.getEventName() );
		buffer.appendString( "|" );
		
		/**
		 * set the event method descprtion
		 */
		buffer
			.appendString( evtMethodDesc.getMethodName() )
			.appendString(":")
			.appendString( evtMethodDesc.getClzName())
			;
		buffer.appendString( "|" );
		
		
		/**
		 * convert parameter to string
		 */
		buffer.appendBytes( convertToBytes( s.getRuntimeArgument() ));


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
	
	
	private byte[] convertToBytes(Collection<RuntimeParam> params) {
		byte[] result = null;
		
		// --- return  parameter ---
		StringBuilder paramTypeStr = new StringBuilder();
		StringBuilder paramNameStr = new StringBuilder();
		StringBuilder paramValueStr = new StringBuilder();
		
		RuntimeParam[] paramArrays = params.toArray(new RuntimeParam[0]);
		
		// --- parse param array ---
		for (int i = 0; i < paramArrays.length ; i++) {
			RuntimeParam param = paramArrays[i];
			
			if (paramNameStr.length() > 0) {
				paramNameStr.append(",");
			}
			
			if (paramTypeStr.length() > 0) {
				paramTypeStr.append(",");
			}
			
			paramNameStr.append( param.getParamName() );
			paramTypeStr.append( param.getParamClzType().getName() );
			byte[] contVal = convertParamValueToBytes( param.getParamVal() );
			paramValueStr.append( contVal.length ).append("`").append( new String(contVal,Charset.forName("UTF-8")) );
		}
		
		
		
		if (paramTypeStr.length() == 0) {
			result = "nil".getBytes(Charset.forName("UTF-8"));
		} else {
			
			// --- merge content ---
			result = Bytes.concat( 
				paramNameStr.toString().getBytes(Charset.forName("UTF-8")),
				";".getBytes(Charset.forName("UTF-8")),
				paramTypeStr.toString().getBytes(Charset.forName("UTF-8")),
				";".getBytes(Charset.forName("UTF-8")),
				paramValueStr.toString().getBytes(Charset.forName("UTF-8"))
				);
			
			
		}
		
		return result;
	}
	
	private byte[] convertParamValueToBytes(java.io.Serializable paramValue ) {
		byte[] result = new byte[0];
		if (paramValue instanceof String) {
			result = ((String)paramValue).getBytes(Charset.forName("UTF-8"));
		}
		
		return result;
		
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
