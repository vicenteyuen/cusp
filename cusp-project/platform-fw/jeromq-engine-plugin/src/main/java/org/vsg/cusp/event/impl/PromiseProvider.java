package org.vsg.cusp.event.impl;

import javax.inject.Provider;

import org.vsg.cusp.concurrent.Promise;
import org.vsg.cusp.concurrent.impl.PromiseImpl;

public class PromiseProvider implements Provider<Promise<?>> {

	

	@Override
	public Promise<?> get() {
		
		Promise promise = new PromiseImpl();
		

		
		return promise;
	}

}
