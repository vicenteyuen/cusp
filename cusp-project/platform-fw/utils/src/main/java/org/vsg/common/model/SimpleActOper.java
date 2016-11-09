/**
 * 
 */
package org.vsg.common.model;

import java.io.Serializable;

/**
 * @author vison
 *
 */
public class SimpleActOper implements ActOper, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6072419304412849762L;

	private String actorId;
	
	private String groupId;
	
	private String activeRoleId;
	
	
	public SimpleActOper() {
		actorId = "";
	}
	
	public SimpleActOper(String actorId) {
		this.actorId = actorId;
	}
	
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public void setActiveRoleId(String activeRoleId) {
		this.activeRoleId = activeRoleId;
	}

	/* (non-Javadoc)
	 * @see org.vsg.common.model.ActOper#getActorId()
	 */
	public String getActorId() {
		// TODO Auto-generated method stub
		return actorId;
	}

	/* (non-Javadoc)
	 * @see org.vsg.common.model.ActOper#getGroupId()
	 */
	public String getGroupId() {
		// TODO Auto-generated method stub
		return groupId;
	}

	public String getActiveRoleId() {
		// TODO Auto-generated method stub
		return activeRoleId;
	}

	
	
}
