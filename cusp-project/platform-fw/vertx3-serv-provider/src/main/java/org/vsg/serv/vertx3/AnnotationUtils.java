package org.vsg.serv.vertx3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AnnotationUtils {
	
	
	
	public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
		
		A[] allAnno =  method.getAnnotationsByType(annotationType); 
		return allAnno[0];
	}	

	public static <A extends Annotation> A[] getAnnotations(Method method, Class<A> annotationType) {
		
		A[] allAnno =  method.getAnnotationsByType(annotationType); 
		return allAnno;
	}	
	
	
}
