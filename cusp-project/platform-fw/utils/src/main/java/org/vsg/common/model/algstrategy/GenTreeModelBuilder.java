package org.vsg.common.model.algstrategy;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.vsg.common.model.TreeModel;
import org.vsg.common.model.json.TreeNode;

public class GenTreeModelBuilder<T extends TreeModel> {

	private Map<String,T> model;
	
	
	public GenTreeModelBuilder(Map<String,T> orgModel) {
		this.model = orgModel;
	}
	
	
	public void generate() {
		
		Map<String , TreeNode> outputTreeNode = new LinkedHashMap<String , TreeNode>();
		
		Set<T> nodeWithParentSet = new LinkedHashSet<T>();
		
		for ( T entity : model.values() ) {
			
			TreeNode tree = new TreeNode();
			tree.setId( entity.getId() );
			tree.setText( entity.getName() );
			tree.setLeaf(true);
			if (entity.getParent() != null) {
				nodeWithParentSet.add( entity );
			}
			
			outputTreeNode.put( entity.getId() , tree);
			resultMap.put(entity.getId(), tree);

		}


		Set<String> dupicateNodeIds = new LinkedHashSet<String>();
		
		for (T disWithPar : nodeWithParentSet) {
			String parentId = disWithPar.getParent().getId();
			
			TreeNode treeNode = outputTreeNode.get( parentId );
			Collection<TreeNode> children = treeNode.getChildren();
			if ( children == null) {
				children = new Vector<TreeNode>();
				treeNode.setLeaf(false);
			}
			
			
			// --- get child node 
			TreeNode child = outputTreeNode.get( disWithPar.getId() );
			
			dupicateNodeIds.add( child.getId() );
			
			children.add( child );
			
			treeNode.setChildren( children );

		}

		for (String nodeId : dupicateNodeIds) {
			outputTreeNode.remove( nodeId );
		}
		
		result.addAll( outputTreeNode.values() );		
	}
	
	private Set<TreeNode> result = new LinkedHashSet<TreeNode>();
	
	public Set<TreeNode> getResultSet() {
		return result;
	}
	
	
	private Map<String , TreeNode> resultMap = new LinkedHashMap<String , TreeNode>();
	
	public Map<String, TreeNode> getResultMap() {
		return resultMap;
	}	
	
}
