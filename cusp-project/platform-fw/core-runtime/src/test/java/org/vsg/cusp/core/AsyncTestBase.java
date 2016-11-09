package org.vsg.cusp.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncTestBase {

	private static final Logger log = LoggerFactory
			.getLogger(AsyncTestBase.class);

	@Rule
	public TestName name = new TestName();

	private volatile Throwable throwable;

	private volatile String mainThreadName;

	private Map<String, Exception> threadNames = new ConcurrentHashMap<>();

	private volatile boolean awaitCalled;

	@Before
	public void before() throws Exception {
		setUp();
	}

	private volatile boolean tearingDown;

	@After
	public void after() throws Exception {
		
		tearDown();

	}

	protected void setUp() throws Exception {
		log.info("Starting test: " + this.getClass().getSimpleName() + "#"
				+ name.getMethodName());
		mainThreadName = Thread.currentThread().getName();
		tearingDown = false;
		waitFor(1);

	}

	private CountDownLatch latch;

	protected void waitFor(int count) {
		latch = new CountDownLatch(count);
	}

	protected void await() {
		await(2, TimeUnit.MINUTES);
	}

	public void await(long delay, TimeUnit timeUnit) {
		if (awaitCalled) {
			throw new IllegalStateException("await() already called");
		}
		awaitCalled = true;
		try {
			boolean ok = latch.await(delay, timeUnit);
			if (!ok) {
				// timed out
				throw new IllegalStateException(
						"Timed out in waiting for test complete");
			} else {
				rethrowError();
			}
		} catch (InterruptedException e) {
			throw new IllegalStateException("Test thread was interrupted!");
		}
	}

	private void rethrowError() {
		if (throwable != null) {
			if (throwable instanceof Error) {
				throw (Error) throwable;
			} else if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else {
				// Unexpected throwable- Should never happen
				throw new IllegalStateException(throwable);
			}

		}
	}

	protected void tearDown() throws Exception {
		latch.countDown();
	}

	protected void checkThread() {
		threadNames.put(Thread.currentThread().getName(), new Exception());
	}

	@Test
	public void testCase01() throws Exception {
		System.out.println(123);

	}

}
