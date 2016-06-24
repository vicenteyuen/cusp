package org.vsg.cusp.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.vsg.cusp.engine.zmq.JeroMQServEngine;

public class JeroMQServEngineTest {
	

	public static void main(String[] args) {
		
		Map<String,String> arguments = new LinkedHashMap<String,String>();
		
		JeroMQServEngine engine = new JeroMQServEngine();
		
		engine.init( arguments );
		
		engine.start();		
	}	
	
}
