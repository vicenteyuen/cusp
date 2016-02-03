/**
 * 
 */
package org.vsg.cusp.bootstrap;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.vsg.cusp.serv.api.EngineProvider;

/**
 * @author ruanweibiao
 *
 */
public class BootstrapAgent {
	
	private static BootstrapAgent inst;
	
	
	
	public static final short RUNNING = 1;
	
	public static final short STOPPED = 0;
	
	
	
	private ClassLoader parentClassLoader;
	
	
	private List<String> runtimeClasspathElements = new Vector<String>();
	
	
	private short status;
	
	private BootstrapAgent() {
		
		
	}
	
	
	
	public static BootstrapAgent getInstance() {
		if ( inst == null) {
			inst = new BootstrapAgent();
		}
		return inst;
	}
	
	
	private File platformHome;
	
	
	
	public File getPlatformHome() {
		return platformHome;
	}



	public void setPlatformHome(File platformHome) {
		this.platformHome = platformHome;
	}


	/**
	 * defined runtime class loader
	 */
	private ClassLoader runtimeContainerClsLoader;
	
	/**
	 * set parent class loader for this agent
	 * @param parentClassLoader
	 */
	public void setParentClassLoader(ClassLoader parentClassLoader ) {
		this.parentClassLoader = parentClassLoader;
	}
	
	
	public void startup(String[] args) {
		
		
		// ---- add classpath to class loader --
		initRuntimeClasspath();
		
		
		/**
		 * load all serivce provider under runtime platform
		 * create one thread for the running provider
		 */
		try {
			
			Class<EngineProvider> cls = (Class<EngineProvider>)runtimeContainerClsLoader.loadClass("org.vsg.serv.vertx3.EngineProviderBootstrap");
			
			EngineProvider ep = cls.newInstance();
			ep.setRuntimeClassLoader(runtimeContainerClsLoader);
			ep.start();
			
			/*
			Class<?> cls = (Class<?>)runtimeContainerClsLoader.loadClass("org.vsg.serv.vertx3.EngineProviderBootstrap");

			Object inst = cls.newInstance();

			
			try {
				Method rclMethod = cls.getMethod("setRuntimeClassLoader", ClassLoader.class);
				
				
				Method startMethod = cls.getMethod("start");
				
				try {
					
					rclMethod.invoke( inst , runtimeContainerClsLoader);
					
					startMethod.invoke( inst );
				
				} catch (IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/



			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// --- set the status --
		this.status = RUNNING;
	}
	
	
	public void shutdown() {
		this.status = STOPPED;
	}
	
	
	private void initRuntimeClasspath() {
		
		
		File libPaths = new File(this.platformHome , "lib");
		
		File[] allJars = libPaths.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				boolean isFile = pathname.isFile();
				if (!isFile) {
					return false;
				}
				boolean passed = pathname.getName().toLowerCase().endsWith(".jar");
				return passed;
			}
			
		});
		
		Set<URL> clsloaderUrls = new LinkedHashSet<URL>();
		
		try {
			
			if (allJars != null) {
				
				for (File jar : allJars) {
					clsloaderUrls.add(jar.toURI().toURL() );
				}
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		
		if (this.runtimeClasspathElements != null) {
			

			for (String classPathElement : this.runtimeClasspathElements) {
				File classPathElementFile = new File( classPathElement );
                try {
					if ( classPathElementFile.isDirectory() ) {
						URL clsPathEleFile = classPathElementFile.toURI().toURL();
						clsloaderUrls.add( clsPathEleFile );
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
			// --- binding class loader ---
			if (clsloaderUrls.isEmpty()) {
				return;
			}

		}
		
		// --- build the runnting loader 
		runtimeContainerClsLoader =  new URLClassLoader( clsloaderUrls.toArray(new URL[0]) , this.parentClassLoader );

		
	}
	
	
	public List<String> getRuntimeClasspathElements() {
		return runtimeClasspathElements;
	}



	public void setRuntimeClasspathElements(List<String> runtimeClasspathElements) {
		this.runtimeClasspathElements = runtimeClasspathElements;
	}



	public short getStatus() {
		
		return status;
		
	}
	
	
	
	
	
	

}
