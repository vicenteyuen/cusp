package com.testcase;

import org.jdeferred.DeferredManager;
import org.jdeferred.impl.DefaultDeferredManager;
import org.junit.Test;

public class DefferedTestCase {
	
	@Test
	public void testDeffered() throws Exception{
		DeferredManager dm = new DefaultDeferredManager();		
		dm.when(()->{return "Hello"; },() -> { return "World"; }
				).done(rs ->
				  rs.forEach(r -> System.out.println(r.getResult()))
				);
	}

}
