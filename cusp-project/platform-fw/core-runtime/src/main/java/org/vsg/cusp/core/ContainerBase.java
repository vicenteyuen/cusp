/**
 * 
 */
package org.vsg.cusp.core;

import java.io.File;

/**
 * @author Vicente Yuen
 *
 */
public class ContainerBase {
	

	protected ClassLoader parentClassLoader;
	
	// --- start container base 
	
	public void init() {
		
	}
	
	
	public ClassLoader getParentClassLoader() {
        if (parentClassLoader != null)
            return (parentClassLoader);
        return (ClassLoader.getSystemClassLoader());
	}

	public void setParentClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}
	
	private File cuspHome;
	
	public File getCuspHome() {
		return cuspHome;
	}

	public void setCuspHome(File cuspHome) {
		this.cuspHome = cuspHome;
	}


	private void loadMicoCompsLibs() {
		
	}
	

}
