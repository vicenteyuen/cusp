/**
 * 
 */
package org.vsg.cusp.platform.engine.plugin.nettyresteasy;

import org.junit.BeforeClass;
import org.junit.Test;
import org.vsg.cusp.platform.startup.Bootstrap;


/**
 * @author Vicente Ruan
 *
 */
public class BootstrapAppTest {

	@BeforeClass
	public static void initClass() {
		// --- set home directory ---
		System.setProperty("cusp.home","../src/home");
		
		String[] args = new String[]{"start"};
		Bootstrap.main(args);		
		
	}
	
	@Test
	public void test_startupServer() throws Exception {
		
	}
	
}
