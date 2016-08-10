/**
 * 
 */
package org.vsg.cusp.event;

/**
 * @author Vicente Yuen
 *
 */
public class RuntimeParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4249721276712489883L;

	private String paramName;
	
	private Class<?> paramClzType;
	
	private java.io.Serializable paramVal;
	
	public RuntimeParam() {
		
	}
	
	public RuntimeParam(String paramName , Class<?> paramClzType , java.io.Serializable paramVal) {
		this.paramClzType = paramClzType;
		this.paramName = paramName;
		this.paramVal = paramVal;
	}
	
	

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Class<?> getParamClzType() {
		return paramClzType;
	}

	public void setParamClzType(Class<?> paramClzType) {
		this.paramClzType = paramClzType;
	}

	public java.io.Serializable getParamVal() {
		return paramVal;
	}

	public void setParamVal(java.io.Serializable paramVal) {
		this.paramVal = paramVal;
	}
	
	
	
}
