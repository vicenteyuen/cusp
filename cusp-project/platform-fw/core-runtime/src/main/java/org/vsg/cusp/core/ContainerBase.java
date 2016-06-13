/**
 * 
 */
package org.vsg.cusp.core;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;

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
		
		try {
			// --- load the share libs ---
			ClassLoader clsLoader = getShareClassLoader(shareLibFolder);
			
			Class cls = clsLoader.loadClass("org.vsg.cusp.product.pub.TestClass");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ClassLoader getShareClassLoader(File shareLibFolder) throws Exception {
		
		File[] files = shareLibFolder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				String fileName = pathname.getName();
				boolean result = fileName.endsWith(".jar");
				return result;
			}
			
		});
		
		URL[] jarUrls = new URL[files.length];
		for (int i = 0 ; i < files.length ; i++) {
			jarUrls[i] = files[i].toURI().toURL();

		}
		
		URLClassLoader urlClsLoader = new URLClassLoader(jarUrls , this.parentClassLoader);

		return urlClsLoader;
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
