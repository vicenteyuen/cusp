package org.vsg.cusp.core;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author ruanweibiao
 *
 */
public class MicroCompInjector {
	
	
	//private Injector injector;
	
	private File homePath;
	
	private ClassLoader homeClassLoader;
	
	private Map<Class<?>, Collection<Class<?>>> annotationMaps;
	
	private String contextPath;

	/*
	public Injector getInjector() {
		return injector;
	}

	public void setInjector(Injector injector) {
		this.injector = injector;
	}
	*/

	public File getHomePath() {
		
		return homePath;
	}

	public void setHomePath(File homePath) {
		this.homePath = homePath;
	}

	public ClassLoader getHomeClassLoader() {
		return homeClassLoader;
	}

	public void setHomeClassLoader(ClassLoader homeClassLoader) {
		this.homeClassLoader = homeClassLoader;
	}

	public Map<Class<?>, Collection<Class<?>>> getAnnotationMaps() {
		return annotationMaps;
	}

	public void setAnnotationMaps(Map<Class<?>, Collection<Class<?>>> annotationMaps) {
		this.annotationMaps = annotationMaps;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	
	
}
