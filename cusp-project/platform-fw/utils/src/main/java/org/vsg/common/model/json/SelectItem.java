/**
 * 
 */
package org.vsg.common.model.json;

import java.io.Serializable;

/**
 * @author Bill Vison
 *
 */
public class SelectItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8044576846193190499L;

	private String id;
	
	private String name;
	

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
