/**
 * 
 */
package org.vsg.cusp.platform.api.servengines;

import java.util.Map;

import org.vsg.common.async.AsyncResult;
import org.vsg.common.async.Callback;
import org.vsg.cusp.platform.api.LifecycleState;
import org.vsg.cusp.platform.core.api.ServEngine;

/**
 * @author vison
 *
 */
public class TestEngine implements ServEngine {



	@Override
	public <T> void start(Callback<AsyncResult<T>> callback) {
		// TODO Auto-generated method stub
		System.out.println("start test engine.");
	}

	@Override
	public <T> void stop(Callback<AsyncResult<T>> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void init(Map<String, String> arguments, Callback<AsyncResult<T>> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void destory(Callback<AsyncResult<T>> callback) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.api.ServEngine#getState()
	 */
	@Override
	public LifecycleState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.api.ServEngine#setState(org.vsg.cusp.platform.api.LifecycleState)
	 */
	@Override
	public void setState(LifecycleState newState) {
		// TODO Auto-generated method stub

	}

}
