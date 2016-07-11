package org.vsg.cusp.event.flow.impl;

import javax.inject.Provider;

import org.vsg.cusp.event.flow.Promise;
import org.vsg.cusp.event.impl.PromiseImpl;

public class PromiseProvider implements Provider<Promise> {

	@Override
	public Promise get() {
		// TODO Auto-generated method stub
		PromiseImpl promise = new PromiseImpl();
		return promise;
	}

}
