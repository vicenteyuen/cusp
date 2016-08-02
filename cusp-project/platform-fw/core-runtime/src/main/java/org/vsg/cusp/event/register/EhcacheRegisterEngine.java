/**
 * 
 */
package org.vsg.cusp.event.register;

import java.util.Map;

import javax.inject.Inject;

import org.ehcache.CacheManager;
import org.vsg.cusp.core.ServEngine;

/**
 * @author vison
 *
 */
public class EhcacheRegisterEngine implements ServEngine {

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
		// TODO Auto-generated method stub
		System.out.println("start chche");

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.Service#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> arguments) {
		// TODO Auto-generated method stub

	}

}
