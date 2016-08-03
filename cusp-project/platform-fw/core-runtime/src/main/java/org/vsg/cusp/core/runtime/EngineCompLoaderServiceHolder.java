/**
 * 
 */
package org.vsg.cusp.core.runtime;

import java.util.List;
import java.util.Vector;

import org.vsg.cusp.core.EngineCompLoaderService;
import org.vsg.cusp.core.ServiceHolder;

/**
 * @author Vicente Yuen
 *
 */
public class EngineCompLoaderServiceHolder implements ServiceHolder{
	
	
	private List<EngineCompLoaderService> engineCompLoaderServices = new Vector<EngineCompLoaderService>();

	@Override
	public void addEngineCompLoaderService(
			EngineCompLoaderService compLoaderService) {
		// TODO Auto-generated method stub
		
		this.engineCompLoaderServices.add( compLoaderService );
		
		
	}

	@Override
	public List<EngineCompLoaderService> getEngineCompLoaderServices() {
		// TODO Auto-generated method stub
		return engineCompLoaderServices;
	}

	
	
}
