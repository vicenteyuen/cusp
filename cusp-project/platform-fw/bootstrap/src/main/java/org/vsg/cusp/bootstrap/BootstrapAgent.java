/**
 * 
 */
package org.vsg.cusp.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.vsg.cusp.serv.api.ServiceProvider;

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
			
			
			Class<ServiceProvider> cls = (Class<ServiceProvider>)runtimeContainerClsLoader.loadClass("org.vsg.serv.vertx3.ServiceProviderBootstrap");
			ServiceProvider inst = cls.newInstance();
			
			inst.setRuntimeClassLoader(runtimeContainerClsLoader);
			
			inst.start();
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
		
		if (this.runtimeClasspathElements != null) {
			
			Set<URL> clsloaderUrls = new LinkedHashSet<URL>();
			for (String classPathElement : this.runtimeClasspathElements) {
				File classPathElementFile = new File( classPathElement );
                try {
					if ( classPathElementFile.isDirectory() ) {
						URL clsPathEleFile = classPathElementFile.toURI().toURL();
						clsloaderUrls.add( clsPathEleFile );
						System.out.println( "adding classPathElementFile " + classPathElementFile.toURI().toString() );
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
			
			// --- build the runnting loader 
			runtimeContainerClsLoader =  new URLClassLoader( clsloaderUrls.toArray(new URL[0]) , this.parentClassLoader );

		}
		
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
