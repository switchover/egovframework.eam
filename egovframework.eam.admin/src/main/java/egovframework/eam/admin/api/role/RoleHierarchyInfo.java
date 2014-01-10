package egovframework.eam.admin.api.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "roles_hierarchy")
@IdClass(RoleHierarchyPK.class)
@XmlRootElement(name="roleHierarchy")
public class RoleHierarchyInfo {
	@Id
    @Column(name="parent_role", length=50)
	private String parentRole;
	
	@Id
	@Column(name="child_role", length=50)
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
