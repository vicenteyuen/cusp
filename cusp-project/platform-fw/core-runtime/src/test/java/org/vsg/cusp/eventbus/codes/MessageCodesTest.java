/**
 * 
 */
package org.vsg.cusp.eventbus.codes;

import org.junit.Test;
import org.vsg.cusp.eventbus.spi.Buffer;

/**
 * @author Vicente Yuen
 *
 */
public class MessageCodesTest {
	
	@Test
	public void test_bytes_case1() throws Exception {
		Buffer buffer = Buffer.factory.buffer();
		
		buffer.appendBytes("hello world".getBytes() );
		
		
		
	}

}
