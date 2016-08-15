/**
 * 
 */
package com.testcase;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

/**
 * @author ruanweibiao
 *
 */
public class AnotherTest {

	@Test
	public void testCase1() throws Exception {

		CompletableFuture<Integer> future = CompletableFuture
				.supplyAsync(() -> {
					return 100;
				});
		CompletableFuture<Void> f = future.thenRun(() -> {
			System.out.println("finished");
		});
		System.out.println(f.get());
	}

}
