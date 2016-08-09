/**
 * 
 */
package org.vsg.cusp.concurrent.impl;

import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.concurrent.PromiseFactory;

/**
 * @author ruanweibiao
 *
 */
public class PromiseFactoryImpl implements PromiseFactory {

	/* (non-Javadoc)
	 * @see org.vsg.cusp.concurrent.PromiseFactory#getPromise()
	 */
	@Override
	public <T> Promise<T> getPromise() {
		
		PromiseImpl imple = new PromiseImpl();

		return imple;
	}

}
