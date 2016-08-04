/**
 * 
 */
package org.vsg.cusp.core.runtime;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.EngineCompLoaderService;
import org.vsg.cusp.core.ServiceHolder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Injector;

/**
 * @author ruanweibiao
 *
 */
public class MicroComponentInitializer implements Runnable {
	
	private ContainerBase container;
	
	private String compName;
	
	private static Logger logger = LoggerFactory.getLogger(MicroComponentInitializer.class);		
	
	String getCompName() {
		return compName;
	}

	void setCompName(String compName) {
		this.compName = compName;
	}

	ContainerBase getContainer() {
		return container;
	}

	void setContainer(ContainerBase container) {
		this.container = container;
	}




	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		Injector parentInjector = container.getInjector();

		ClassLoader compClassLoader = getContainer().getComponentsClassLoader().get( this.compName );
		
		File homePath = getContainer().getComponentsPath().get(this.compName);
		
		// --- config home path --
		configration(homePath);
		
		// --- scan supported annotations ---
		scanAnnotaions(scanPackages , compClassLoader);
		
		
		// --- get booted engines ---
		ServiceHolder  serviceHolder = parentInjector.getInstance( ServiceHolder.class);
		
		List<EngineCompLoaderService> engineCompLoaderServices =  serviceHolder.getEngineCompLoaderServices();		
		
		
		
		
		Set<Class> preScanClzes = new LinkedHashSet<Class>();
		
		
		Map<Class, Collection<Class>> annotationMapping = new LinkedHashMap<Class , Collection<Class>>();
		
		for (EngineCompLoaderService  engineCompLoaderService : engineCompLoaderServices ) {
			engineCompLoaderService.scanClassForAnnoation( homePath , compClassLoader ,  annotationMapping );
		
		}
		
		
		// --- create micro component module ---
		PreScanAnnotationModule psaModule = new PreScanAnnotationModule();
		psaModule.setHomePath( homePath );
		psaModule.setHomeClassLoader( compClassLoader );
		psaModule.setPreDefinedScanAnnotation( preScanClzes );
		psaModule.setScanPackages(scanPackages);
		
		
		Injector  inject = parentInjector.createChildInjector(psaModule);

		

	}
	
	private Set<String> scanPackages = new LinkedHashSet<String>();
	
	private void configration(File homePath) {
		File confFile = new File(homePath , "comp.json");
		
		if (!confFile.exists()) {
			logger.warn("Could not find \"comp.json\" file under the " + homePath);
		}
		
		
		String contextPath = "";
		// --- init config setting ---
		try {
			// --- scan rest api , parse json file ---
			JSONObject jObj = (JSONObject)JSON.parse( IOUtils.toString(confFile.toURI() , "utf-8") );
			
			// --- load to package scan package ---
			JSONArray scanPackageArr = jObj.getJSONArray("package-scan");
	    	for (Iterator<String> spIter =  (Iterator<String>)(Iterator)scanPackageArr.iterator() ; spIter.hasNext(); ) {
	    		String onePackage = spIter.next();
	    		scanPackages.add( onePackage);
	    	}
	    	
	    	contextPath = jObj.getString("context-path") == null ? "" : jObj.getString("context-path");
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 
	 * @param candidatePackages
	 * @return the map of class mapping annoation class
	 */
	private Map<Class<?>, Collection<Class<?>>> scanAnnotaions(Set<String> candidatePackages , ClassLoader compClassLoader) {
		
		/**
		 * scan support annotations
		 */
		
		Set<Class> annotationsSupported = new LinkedHashSet<Class>();
    	try {
    		
    		Enumeration<URL>  urls =  getClass().getClassLoader().getResources("META-INF/annotations-scan");
    		while (urls.hasMoreElements()) {
    			URL url = urls.nextElement();
    			
    			if (url.getProtocol().equals("jar")) {
    				JarURLConnection uc = (JarURLConnection)url.openConnection();
    				List<String> allLines = IOUtils.readLines(uc.getInputStream() , Charset.forName("UTF-8"));

        			for (String line : allLines) {
        				
        				if (line.startsWith("#")) {
        					continue;
        				}
        				
        				Class<?> cls = (Class<?>)Class.forName( line );
        				
        				annotationsSupported.add( cls );
        				
        			}
    			}
    			
    		}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
    	
    	/**
    	 * scan all class for support 
    	 */
    	
    	
    	
		return null;
	}


}
