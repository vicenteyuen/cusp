/**
 * 
 */
package org.vsg.common.model.json;

import java.io.Serializable;

/**
 * @author Bill Vison
 *
 */
public class ComboxModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8044576846193190499L;

	private String value;
	
	private String index;
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String name) {
		this.index = name;
	}

	
	
}
