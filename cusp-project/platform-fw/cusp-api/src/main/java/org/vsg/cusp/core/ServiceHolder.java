package org.vsg.cusp.core;

import java.util.List;

public interface ServiceHolder {
	
	
	void addEngineCompLoaderService(EngineCompLoaderService compLoaderService);
	
	List<EngineCompLoaderService> getEngineCompLoaderServices();

}
