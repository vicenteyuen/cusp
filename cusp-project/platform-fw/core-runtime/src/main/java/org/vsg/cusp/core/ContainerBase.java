/**
 * 
 */
package org.vsg.cusp.core;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vicente Yuen
 *
 */
public class ContainerBase {
	

	protected ClassLoader parentClassLoader;
	
	
	private static Logger logger = LoggerFactory.getLogger( ContainerBase.class );
	
	// --- start container base 
	
	public void init() {
		
		File microCompsFolder = new File(cuspHome , "mico-comps");
		
		if (!microCompsFolder.exists()) {
			logger.error("Could not found folder \"mico-comps\".");
			return ;
		}
		
		// --- share folder ---
		File shareLibFolder = new File(microCompsFolder , "share");
		
		// --- load the share libs ---
		getShareClassLoader(shareLibFolder);
	}
	
	
	public ClassLoader getShareClassLoader(File shareLibFolder) {
		return null;
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
