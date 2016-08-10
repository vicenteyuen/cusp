package org.vsg.cusp.event.flow;

import org.vsg.cusp.concurrent.Promise;

public interface PromiseAware {
	
	void setPromise(Promise promise);

}
