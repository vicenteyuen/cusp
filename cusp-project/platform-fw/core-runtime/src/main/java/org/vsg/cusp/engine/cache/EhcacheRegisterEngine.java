/**
 * 
 */
package org.vsg.cusp.engine.cache;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import org.ehcache.CacheManager;
import org.vsg.cusp.core.CountDownLatchAware;
import org.vsg.cusp.core.LifecycleState;
import org.vsg.cusp.core.ServEngine;

/**
 * @author vison
 *
 */
public class EhcacheRegisterEngine implements ServEngine ,  CountDownLatchAware {

	private CacheManager cacheManager;
	
	
	
	@Inject
	public EhcacheRegisterEngine(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.Service#start()
	 */
	@Override
	public void start() throws Exception {
		
		setState( LifecycleState.STARTING );
		
		countDownLatch.countDown();
		
		setState( LifecycleState.STARTED );

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.Service#stop()
	 */
	@Override
	public void stop() {

	}

    private volatile LifecycleState state = LifecycleState.NEW;	

	
	@Override
	public LifecycleState getState() {
		// TODO Auto-generated method stub
		return state;
	}
	

	@Override
	public void setState(LifecycleState newState) {
		// TODO Auto-generated method stub
		state = newState;
	}	

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> arguments) {

	}
	
	private CountDownLatch countDownLatch;	

	@Override
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		// TODO Auto-generated method stub
		this.countDownLatch = countDownLatch;
		
	}
	
	
	

}
