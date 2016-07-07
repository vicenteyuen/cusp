/**
 * 
 */
package org.vsg.cusp.eventbus;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.AsyncTestBase;
import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.eventbus.impl.EventBusOptions;

/**
 * @author Vicente Yuen
 *
 */
public class EventBusTestBase extends AsyncTestBase {
	
	private static Logger log = LoggerFactory.getLogger( EventBusTestBase.class );
	
	private EventBus eventBus;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		EventBusOptions options = new EventBusOptions();
		

		ZmqEventBusImplEndPoint eventBusImpl = new ZmqEventBusImplEndPoint(options);
		
		eventBusImpl.start(ar2 -> {
			System.out.println(ar2);
		});
		
		eventBus = eventBusImpl;
		
		
	}
	
	@Test
	public void test_00_sender() throws Exception {
		log.info("send message  ...");
		eventBus.send("test.query", "Hello, sender test. ");

	}

}
