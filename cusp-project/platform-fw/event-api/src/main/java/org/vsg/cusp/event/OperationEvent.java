package org.vsg.cusp.event;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Vicente Yuen
 *
 */
public interface OperationEvent {
	
	// ---- associa method ---
	void setRuntimeArgument(Collection<RuntimeParam> arguments);
	
	Collection<RuntimeParam> getRuntimeArgument();
	
	EventMethodDescription getMethodDescription();
	
	void setMethodDescription(EventMethodDescription methodDescription);


}
