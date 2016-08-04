/**
 * 
 */
package org.vsg.cusp.core.runtime;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.ws.rs.Path;

import org.vsg.cusp.core.utils.ClassFilter;
import org.vsg.cusp.core.utils.ClassUtils;

import com.google.inject.AbstractModule;

/**
 * @author Vicente Yuen
 *
 */
public class PreScanAnnotationModule extends AbstractModule {
	
	private File homePath;
	
	private ClassLoader homeClassLoader;
	
	private Set<Class> preDefinedScanAnnotation = new LinkedHashSet<Class>();
	
	private Set<String> scanPackages = new LinkedHashSet<String>();
	

	File getHomePath() {
		return homePath;
	}


	void setHomePath(File homePath) {
		this.homePath = homePath;
	}


	ClassLoader getHomeClassLoader() {
		return homeClassLoader;
	}

	void setHomeClassLoader(ClassLoader homeClassLoader) {
		this.homeClassLoader = homeClassLoader;
	}

	Set<Class> getPreDefinedScanAnnotation() {
		return preDefinedScanAnnotation;
	}

	void setPreDefinedScanAnnotation(Set<Class> preDefinedScanAnnotation) {
		this.preDefinedScanAnnotation = preDefinedScanAnnotation;
	}


	Set<String> getScanPackages() {
		return scanPackages;
	}


	void setScanPackages(Set<String> scanPackages) {
		this.scanPackages = scanPackages;
	}


	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		List<Class<?>>  allScanClasses = scanClassesForClass();
		
		// --- bind all instance ---
		for (Class<?> clsInst : allScanClasses) {
			this.bind( clsInst );
		}
		


	}
	
	
	private List<Class<?>> scanClassesForClass() {
		
		
		List<Class<?>> allFoundCls = new Vector<Class<?>>();
		
		// --- show scan package ---
		for (String packageItem : scanPackages) {
			
			List<Class<?>> allCls =  ClassUtils.getClasses( packageItem , this.homeClassLoader , new ClassFilter() {

				@Override
				public boolean filter(Class<?> clsInput) {
					// TODO Auto-generated method stub
					Path pathMarkInClass = clsInput.getAnnotation(Path.class);
					
					Method[] methodsInCls = clsInput.getMethods();
					
					boolean markExisted = pathMarkInClass != null;
					
					for (Method method : methodsInCls) {
						Path pathMarkOnMethod = method.getAnnotation(Path.class);
						
						if (!markExisted) {
							markExisted = pathMarkOnMethod != null;
							break;
						}
					}
					return markExisted;
				}
				
			});
			
			allFoundCls.addAll( allCls );
		}
		
		return allFoundCls;
		
				
	}

}
