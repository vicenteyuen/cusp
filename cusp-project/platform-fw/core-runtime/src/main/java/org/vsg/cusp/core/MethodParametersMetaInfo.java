/**
 * 
 */
package org.vsg.cusp.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * @author Vicente Yuen
 *
 */
public class MethodParametersMetaInfo {

	private Class[] paramCls;
	
	private Object[] params;
	
	private Parameter[] parameters;

	public Class[] getParamCls() {
		return paramCls;
	}

	public void setParamCls(Class[] paramCls) {
		this.paramCls = paramCls;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}
	
	
	public <T extends Annotation> boolean presentAnnotation(int paramIndex , Class<T> annotationClass) {
		Parameter param = this.parameters[paramIndex];
		Object clsInstance = param.getAnnotation( annotationClass );
		return clsInstance != null;
	}
	
	public <T extends Annotation> T receiveAnnotationInst(int paramIndex , Class<T> annotationClass) {
		Parameter param = this.parameters[paramIndex];
		return param.getAnnotation( annotationClass );
	}
	
	
	
	
}
