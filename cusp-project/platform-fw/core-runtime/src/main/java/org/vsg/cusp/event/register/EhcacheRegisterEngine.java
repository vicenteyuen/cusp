/**
 * 
 */
package org.vsg.cusp.event.register;

import java.util.Map;

import javax.inject.Inject;

import org.ehcache.CacheManager;
import org.vsg.cusp.core.EngineCompLoaderService;
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
		

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.Service#stop()
	 */
	@Override
	public void stop() {

	}


	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> arguments) {

	}

}
