
package org.vsg.serv.vertx3;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author visdon ruan
 *
 */
public class Vertx3HttpServerTest {
	
	
	private static Logger logger = LoggerFactory.getLogger(Vertx3HttpServerTest.class);
	
	@Test
	public void test_vertx3HttpServer_case01() throws Exception {
		
    	EngineProviderBootstrap epb = new EngineProviderBootstrap();
    	epb.start();
    	
		
		
	}

	
    public static void main( String[] args )
    {
    	EngineProviderBootstrap epb = new EngineProviderBootstrap();
    	epb.start();
    	
    }
 	 

}
