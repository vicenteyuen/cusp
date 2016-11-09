/**
 * 
 */
package org.vsg.cusp.event;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ruanweibiao
 *
 */
public class DefaultRuntimeParams implements RuntimeParams {
	
	private Map<String, RuntimeParam> existedParams = new LinkedHashMap<String, RuntimeParam>();

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.RuntimeParams#addRuntimeParam(org.vsg.cusp.event.RuntimeParam)
	 */
	@Override
	public void addRuntimeParam(RuntimeParam param) throws Exception{

		Objects.requireNonNull(param.getParamName(), "Runtime param name is not null.");
		
		if (existedParams.containsKey(param.getParamName())) {
			throw new Exception ("Runtime param is existed. ");
		}
		
		existedParams.put( param.getParamName() , param);
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.RuntimeParams#removeRuntimeParam(java.lang.String)
	 */
	@Override
	public RuntimeParam removeRuntimeParam(String paramName) {
		Objects.requireNonNull(paramName, "Runtime param name is not null.");
		RuntimeParam runtimeParam = existedParams.remove(paramName);
		return runtimeParam;

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.event.RuntimeParams#getRuntimeParams()
	 */
	@Override
	public Collection<RuntimeParam> getRuntimeParams() {
		return existedParams.values();
	}

}
