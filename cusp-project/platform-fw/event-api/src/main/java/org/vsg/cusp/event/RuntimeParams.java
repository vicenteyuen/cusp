package org.vsg.cusp.event;

import java.util.Collection;

public interface RuntimeParams {
	
	/**
	 * add runtime param
	 * @param param
	 */
	void addRuntimeParam(RuntimeParam param) throws Exception;

	
	RuntimeParam removeRuntimeParam(String paramName);
	
	
	Collection<RuntimeParam> getRuntimeParams();
}
