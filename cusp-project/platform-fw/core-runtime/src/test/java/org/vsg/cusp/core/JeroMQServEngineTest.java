package org.vsg.cusp.core;

import org.junit.Test;

public class JeroMQServEngineTest {
	
	@Test
	public void test_01_start() throws Exception {
		JeroMQServEngine engine = new JeroMQServEngine();
		engine.start();
	}

}
