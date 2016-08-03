/**
 * 
 */
package org.vsg.cusp.core.runtime;

import java.util.List;

import org.vsg.cusp.core.EngineCompLoaderService;
import org.vsg.cusp.core.ServiceHolder;

import com.google.inject.Injector;

/**
 * @author ruanweibiao
 *
 */
public class MicroComponentInitializer implements Runnable {
	
	private ContainerBase container;
	
	private String compName;
	
	String getCompName() {
		return compName;
	}

	void setCompName(String compName) {
		this.compName = compName;
	}

	ContainerBase getContainer() {
		return container;
	}

	void setContainer(ContainerBase container) {
		this.container = container;
	}




	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		Injector injector = container.getInjector();
		
		// --- get booted engines ---
		ServiceHolder  serviceHolder = injector.getInstance( ServiceHolder.class);
		
		List<EngineCompLoaderService> engineCompLoaderServices =  serviceHolder.getEngineCompLoaderServices();
		
		ClassLoader compClassLoader = getContainer().getComponentsClassLoader().get( this.compName );
		
		for (EngineCompLoaderService  engineCompLoaderService : engineCompLoaderServices ) {
			
			engineCompLoaderService.appendLoadService( compClassLoader );
		
		}


		

	}

}
