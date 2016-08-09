/**
 * 
 */
package org.vsg.cusp.concurrent;

import org.junit.Test;

/**
 * @author ruanweibiao
 *
 */
public class PromiseTestCase {
	
	@Test
	public void testPromise() throws Exception {
		
		PromiseFactory factory = Promise.factory;
		
		Promise promise = factory.getPromise();
		
		System.out.println( promise );

	}

}
