package org.vsg.cusp.core;

import java.io.File;

/**
 * 
 * @author define Engine component loader service
 *
 */
public interface EngineCompLoaderService {
	
	void appendLoadService(File homePath , ClassLoader classLoader);

}
