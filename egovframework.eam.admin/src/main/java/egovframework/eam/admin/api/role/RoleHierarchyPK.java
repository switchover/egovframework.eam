package egovframework.eam.admin.api.role;

import java.io.Serializable;

import javax.persistence.Column;

public class RoleHierarchyPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1512666874421487907L;

    @Column(name="parent_role", length=50)
	private String parentRole;
	
	@Column(name="child_role", length=50)
	private String childRole;
	
	public RoleHierarchyPK() {
	}
	
	public RoleHierarchyPK(String parentRole, String childRole) {
		this.parentRole = parentRole;
		this.childRole = childRole;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof RoleHierarchyPK) {
			RoleHierarchyPK roleHierarchy = (RoleHierarchyPK)obj;
			
			if (roleHierarchy.getParentRole() == null || roleHierarchy.getChildRole() == null) {
				return false;
			}
			
			if (roleHierarchy.getParentRole().equals(parentRole) && roleHierarchy.getChildRole().equals(childRole)) {
				return true;
			}
 		} 

		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();	// TODO
	}

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
