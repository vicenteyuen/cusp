/**
 * 
 */
package org.vsg.cusp.platform.runtime;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.platform.api.Container;

import com.google.inject.Injector;

/**
 * @author Vicente Yuen
 *
 */
public class ContainerBase implements Container{
	

	protected ClassLoader parentClassLoader;
	
	
	private static Logger logger = LoggerFactory.getLogger( ContainerBase.class );
	
	
	private Map<String,ClassLoader> supportCompClsMapping = new LinkedHashMap<String,ClassLoader>();
	
	private Map<String , File> supportedCompsPathMapping = new LinkedHashMap<String, File>();
	
	private Injector injector;
	
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
			ClassLoader shareLibClassLoader = getFolderClassLoader(new File(shareLibFolder,"lib") , parentClassLoader);
			
			File[] subFolder = microCompsFolder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					if (!pathname.isDirectory()) {
						return false;
					}
					String folderName = pathname.getName();
					if (folderName.equalsIgnoreCase("share")) {
						return false;
					}
					return true;
				}
				
			});
			
			
			// --- load component class loader ---
			for (File compFolder : subFolder) {
				ClassLoader compClassLoader = getFolderClassLoader(new File(compFolder,"lib") , shareLibClassLoader);
				supportCompClsMapping.put(compFolder.getName(), compClassLoader);
				supportedCompsPathMapping.put(compFolder.getName() , compFolder);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void	setInjector(Injector injector) {
		this.injector = injector;
	}
	
	public Injector getInjector() {
		return this.injector;
	}
	
	
	private void reflash() {
		
	}
	
	

	public Map<String,ClassLoader> getComponentsClassLoader() {
		return this.supportCompClsMapping;
	}
	
	
	public ClassLoader getFolderClassLoader(File shareLibFolder , ClassLoader parentClassLoader) throws Exception {
		
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
		
		URLClassLoader urlClsLoader = new URLClassLoader(jarUrls , parentClassLoader);

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


	public Map<String, File> getComponentsPath() {
		// TODO Auto-generated method stub
		return this.supportedCompsPathMapping;
	}
	

}
