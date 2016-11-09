/**
 * 
 */
package org.vsg.common.model.json;

import java.util.Collection;

/**
 * @author Bill vison
 *
 */
public class TreeNode {
	
	private String id;
	
	private String text;
	
	private boolean leaf;
	
	private boolean expanded;
	
	private Collection<TreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Collection getChildren() {
		return children;
	}

	public void setChildren(Collection children) {
		this.children = children;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	
	

}
