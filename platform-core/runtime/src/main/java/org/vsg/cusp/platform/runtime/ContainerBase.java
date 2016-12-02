/**
 * 
 */
package org.vsg.cusp.platform.runtime;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

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
	
	public void load() {
		
		File microCompsFolder = new File(cuspHome , "micro-comps");
		
		if (!microCompsFolder.exists()) {
			logger.error("Could not found folder \"micro-comps\".");
			return ;
		}
		
		// --- share folder ---
		File shareLibFolder = new File(microCompsFolder , "share");
		
		ClassLoader  clsLoader = parentClassLoader;
		if (shareLibFolder.exists()) {
			clsLoader = getClassLoaderForDir(parentClassLoader , shareLibFolder);
		}

		try {
			
			
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
					
					// --- check validate micro comps ---
					File confPath = new File(pathname , "conf");
					if (!confPath.exists()) {
						return false;
					}
					
					File confFile = new File(confPath , "conf.json");
					if (!confPath.exists()) {
						return false;
					}					
					return true;
				}
				
			});
			
			
			// --- load component class loader ---

			for (File compFolder : subFolder) {
				ClassLoader compClassLoader = getClassLoaderForDir( clsLoader , compFolder);
				supportCompClsMapping.put(compFolder.getName(), compClassLoader);
				supportedCompsPathMapping.put(compFolder.getName() , compFolder);
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ClassLoader getClassLoaderForDir(ClassLoader parentClassLoader , File compDir) {
		
		ClassLoader outputClzLoader = parentClassLoader;
		
		// --- load the share libs ---
		File folder = new File(compDir,"lib");
		try {
			if (folder.exists()) {
				outputClzLoader = getClassLoaderForJars(folder , outputClzLoader);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			folder = null;
		}
				
		folder = new File(compDir,"classes");
		try {
			if (folder.exists()) {
				outputClzLoader = getClassLoaderForClassesDir(folder , outputClzLoader);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			folder = null;
		}
		
		return outputClzLoader;
		
	}
	
	public void	setInjector(Injector injector) {
		this.injector = injector;
	}
	
	public Injector getInjector() {
		return this.injector;
	}
	
	
	private void init() {
		
	}
	
	

	public Map<String,ClassLoader> getComponentsClassLoader() {
		return this.supportCompClsMapping;
	}
	
	
	public ClassLoader getClassLoaderForJars(File shareLibFolder , ClassLoader parentClassLoader) throws MalformedURLException {
		
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
	
	public ClassLoader getClassLoaderForClassesDir(File shareClassFolder , ClassLoader parentClassLoader) throws MalformedURLException {
		URL[] urls = new URL[]{shareClassFolder.toURI().toURL()};
		URLClassLoader urlClsLoader = new URLClassLoader(urls , parentClassLoader);

		return urlClsLoader;		
	}
	
	@Override
	public Map<String, Properties> getComponentsProps() {
		// TODO Auto-generated method stub
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


	
	

	public Map<String, File> getComponentsPath() {
		// TODO Auto-generated method stub
		return this.supportedCompsPathMapping;
	}
	

}
