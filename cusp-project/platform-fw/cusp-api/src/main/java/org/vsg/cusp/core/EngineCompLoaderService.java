package org.vsg.cusp.core;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author define Engine component loader service
 *
 */
public interface EngineCompLoaderService {
	
	void appendLoadService(File homePath , ClassLoader classLoader);
	
	
	void scanClassForAnnoation(Map<Class, Collection<Class>> annotationClsBinding );
	
}
