/**
 * 
 */
package org.vsg.common.model.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author vison
 *
 */
public class ItemsResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3599417353703444228L;

	private boolean success;
	
	private Collection<? extends java.io.Serializable> root = new LinkedList<java.io.Serializable>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Collection<? extends java.io.Serializable> getRoot() {
		return root;
	}

	public void setRoot(Set<java.io.Serializable> selItems) {
		this.root = selItems;
	}
	
	
	public void addAll(Collection<java.io.Serializable> input) {
		this.root.addAll( (Collection)input );
	}

	
	
}
