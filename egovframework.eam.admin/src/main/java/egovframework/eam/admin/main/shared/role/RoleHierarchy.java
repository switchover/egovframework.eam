package egovframework.eam.admin.main.shared.role;

import java.io.Serializable;

public class RoleHierarchy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2469606280526973136L;

	private String parentRole;
	
	private String childRole;

	public String getParentRole() {
		return parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}

	public String getChildRole() {
		return childRole;
	}

	public void setChildRole(String childRole) {
		this.childRole = childRole;
	}
}
