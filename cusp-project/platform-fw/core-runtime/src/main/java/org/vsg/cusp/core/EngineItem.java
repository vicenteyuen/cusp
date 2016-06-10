/**
 * 
 */
package org.vsg.cusp.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Vicente Yuen
 *
 */
public class EngineItem {
	
	private String className;
	
	private Map<String, String> arguments = new LinkedHashMap<String,String>(); 

	/**
	 * 
	 */
	public EngineItem(String className , Map<String, String> arguments) {
		// TODO Auto-generated constructor stub
		this.className = className;
		this.arguments = arguments;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
	}
	
	

}
