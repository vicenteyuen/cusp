package org.vsg.cusp.event.flow.impl;

import javax.inject.Provider;

import org.vsg.cusp.concurrent.impl.PromiseImpl;
import org.vsg.cusp.event.flow.Promise;

public class PromiseProvider implements Provider<Promise> {

	@Override
	public Promise get() {
		// TODO Auto-generated method stub
		PromiseImpl promise = new PromiseImpl();
		return promise;
	}

}
