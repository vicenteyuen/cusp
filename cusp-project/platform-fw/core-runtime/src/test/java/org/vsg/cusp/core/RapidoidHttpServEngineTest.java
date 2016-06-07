package org.vsg.cusp.core;

import org.junit.Test;

public class RapidoidHttpServEngineTest {
	
	@Test
	public void test_01_start() throws Exception {
		RapidoidHttpServEngine engine = new RapidoidHttpServEngine();
		engine.start();
	}

}
