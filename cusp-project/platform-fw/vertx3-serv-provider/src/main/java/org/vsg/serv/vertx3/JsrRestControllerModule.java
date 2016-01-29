/**
 * 
 */
package org.vsg.serv.vertx3;

import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

/**
 * @author vison
 *
 */
public class JsrRestControllerModule extends AbstractModule {
	
	
	private static Logger logger = LoggerFactory.getLogger( JsrRestControllerModule.class );
	
	private ClassLoader runtimeClsLoader;
	
	
	private Router router;
	
	private Set<String> scanPackages = new LinkedHashSet<String>();
	

	public JsrRestControllerModule(Router router , ClassLoader runtimeClassLoader , Set<String> scanPackages) {
		super();
		this.runtimeClsLoader = runtimeClassLoader;
		this.router = router;
		this.scanPackages = scanPackages;
	}
	
	public ClassLoader getRuntimeClassLoader() {
		return this.runtimeClsLoader;
	}
	
	
	public Set<String> getNeedToScanPackages() {
		return this.scanPackages;
	}
	

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// scan controller 
		
		
        
        RuntimeClsLoaderAnnotationReflection  rcar = new RuntimeClsLoaderAnnotationReflection( getRuntimeClassLoader() );
		
        for (String packageItem : this.scanPackages) {
        	List<Class<?>>  candidateClses =  rcar.findCandidates( packageItem , Path.class);
        	
        	// --- call and bind 
        	for (Class<?> candidate : candidateClses) {
        		bindRestfulPath(candidate);
        	}
        }

	}
	
	
	private void bindRestfulPath(Class<?> candidate) {
		// --- bind rest ---
		if (logger.isDebugEnabled()) {
			logger.debug("scan class controller " + candidate);
		}
		
		String rootPath = null;
		
		Target target = Path.class.getAnnotation(Target.class);
		for (ElementType elementType : target.value()) {
			switch (elementType) {
				case TYPE:
					Path path =  candidate.getAnnotation(Path.class);
					rootPath = path.value();
					break;
				case CONSTRUCTOR:
					break;
					
				case METHOD:
					bindingHandleMethodPath( candidate , rootPath);
					
					/*
					for (Method method : candidate.getMethods()) {
						paths = method.getAnnotationsByType(Path.class); 
						
						//javax.ws.rs.GET hm = AnnotationUtils.getAnnotation(method, javax.ws.rs.GET.class);
						javax.ws.rs.HttpMethod  g =   method.getAnnotation( javax.ws.rs.HttpMethod.class );
						System.out.println(g);
						for (Path path : paths) {
							if (!path.value().startsWith("/")) {
								buildPath.append("/");
							}
							buildPath.append(path.value());
						}						
					}
					*/
					break;
				default:
					break;
			}
		}

		
		
		
		//Path[] values = candidate.getAnnotatedInterfaces().getAnnotationsByType(Path.class);

		//System.out.println(value);
		
	}
	
	
	private void bindingHandleMethodPath(Class<?> candidate , String baseRootPath) {
		
		Method[] methods = candidate.getMethods();
		
		for (Method method : methods) {
			System.out.println("-----------------");
			Annotation[] lists =  method.getAnnotations();
			
			for (Annotation anno : lists) {
				
				

				
				System.out.println( anno.annotationType() ) ;

				
				System.out.println(anno);
			}
			
		}
		
		
	}
	
	
	
	
	

}
