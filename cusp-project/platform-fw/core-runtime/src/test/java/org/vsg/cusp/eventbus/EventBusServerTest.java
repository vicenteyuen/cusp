package org.vsg.cusp.eventbus;

import java.util.LinkedHashMap;
import java.util.Map;

import org.vsg.cusp.engine.zmq.JeroMQEngineModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EventBusServerTest {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String,String> arguments = new LinkedHashMap<String,String>();
		arguments.put("mq.port", "8701");
		
		
		// --- create module ---
		JeroMQEngineModule engineModule = new JeroMQEngineModule();
		engineModule.init( arguments );
		
		
		Injector injector = Guice.createInjector( engineModule );
		


	}

}
