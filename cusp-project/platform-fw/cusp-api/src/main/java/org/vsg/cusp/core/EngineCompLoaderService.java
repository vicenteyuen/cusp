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
	
	void scanClassForAnnoation(File homePath , ClassLoader classLoader , Map<Class, Collection<Class>> annotationClsBinding);
	
}
