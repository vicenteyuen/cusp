/**
 * 
 */
package org.vsg.cusp.platform.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vsg.cusp.platform.startup.Bootstrap;

/**
 * @author vison
 *
 */
public class BootstrapControlTest {
	
	@BeforeClass
	public static void initClass() {
		
		String[] args = new String[]{"start"};
		Bootstrap.main(args);		
		
	}
	
	@AfterClass
	public static void afterClass() {
		
	}
	
	@Test
	public void test_ServerStartup() throws Exception {
		

		
	}

}
