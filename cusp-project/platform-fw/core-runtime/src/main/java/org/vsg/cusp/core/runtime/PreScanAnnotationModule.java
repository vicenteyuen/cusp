/**
 * 
 */
package org.vsg.cusp.core.runtime;

import java.util.Collection;
import java.util.List;

import com.google.inject.AbstractModule;

/**
 * @author Vicente Yuen
 *
 */
public class PreScanAnnotationModule extends AbstractModule {
	
	
	
	private Collection<Class<?>> preInstanceClzes;
	
	
	Collection<Class<?>> getPreInstanceClzes() {
		return preInstanceClzes;
	}

	void setPreInstanceClzes(Collection<Class<?>> preInstanceClzes) {
		this.preInstanceClzes = preInstanceClzes;
	}


	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// TODO Auto-generated method stub

		for (Class<?> clsInst : preInstanceClzes) {
			this.bind( clsInst );
		}
		


	}


}
