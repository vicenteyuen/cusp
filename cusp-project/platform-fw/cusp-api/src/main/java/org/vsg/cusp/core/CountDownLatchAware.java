package org.vsg.cusp.core;

import java.util.concurrent.CountDownLatch;

public interface CountDownLatchAware {
	
	/**
	 * define count down latch object
	 * @param countDownLatch
	 */
	void setCountDownLatch(CountDownLatch countDownLatch);

}
