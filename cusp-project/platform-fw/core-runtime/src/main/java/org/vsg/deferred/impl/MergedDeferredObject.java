/**
 * 
 */
package org.vsg.deferred.impl;
import java.util.List;

import org.vsg.cusp.core.Handler;
import org.vsg.deferred.Promise;


/**
 * @author ruanweibiao
 *
 */
public class MergedDeferredObject extends DeferredObject{

	
	
	public MergedDeferredObject(Promise... promises) {
		
		init(promises);
		
	}
	
	private void init(Promise... promises) {
		
		for (Promise promise : promises) {
			AbstractPromise absPromise = (AbstractPromise)promise;
			
			if (!absPromise.getProgressCallbacks().isEmpty()) {
				this.getProgressCallbacks().addAll( absPromise.getProgressCallbacks() );				
			};
			
			
			if (!absPromise.getDoneCallbacks().isEmpty()) {
				this.getDoneCallbacks().addAll( absPromise.getDoneCallbacks() );
			}
			
			if (!absPromise.getFailCallbacks().isEmpty()) {
				this.getFailCallbacks().addAll( absPromise.getFailCallbacks());
			}			
			
		}
		
		
		
		
		
	}
	
}
