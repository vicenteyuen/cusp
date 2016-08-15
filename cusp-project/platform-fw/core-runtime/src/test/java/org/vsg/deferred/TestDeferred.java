package org.vsg.deferred;

import org.junit.Test;
import org.vsg.deferred.impl.DefaultDeferredManager;

public class TestDeferred {
	
	@Test
	public void testDeferred_when() throws Exception {
		
		DefaultDeferredManager deferredManager = new DefaultDeferredManager();
		
		Promise<Object, Throwable, Void> promise =   deferredManager.when((e) -> {
			System.out.println("base1");
		} , (e) -> {
			System.out.println("base2");
		}, (e) -> {
			System.out.println("base3");
		});
		
		
		promise.succeed( (e) -> {
			System.out.println("successful");
		});
		
		promise.fail( (e) -> {
			System.out.println("fail");
		});
		
		promise.await();
	}
	
	


}
