package org.vsg.cusp.eventbus;

import java.util.Map;

public interface EventFlow {
	
	
	<T> void fire(String eventName , Map<String , Object> fireArguements , Handler<AsyncResult<T>>...handlers);

}
