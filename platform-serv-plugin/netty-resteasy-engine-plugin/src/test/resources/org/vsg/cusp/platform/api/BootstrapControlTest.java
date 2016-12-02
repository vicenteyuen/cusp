package org.vsg.cusp.platform.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.platform.startup.Bootstrap;

public class BootstrapControlTest {
	
	private Logger logger = LoggerFactory.getLogger(BootstrapControlTest.class);

	@BeforeClass
	public static void initClass() {
		// --- set home directory ---
		System.setProperty("cusp.home","../src/home");
		
		String[] args = new String[]{
				"start"
				};
		Bootstrap.main(args);		
		
	}
	
	@AfterClass
	public static void afterClass() {
		
	}
	
	@Test
	public void test_ServerStartup() throws Exception {
		
		logger.info("Start server");
		
	}
	
	
}
